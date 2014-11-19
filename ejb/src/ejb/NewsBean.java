package ejb;

import bean.Article;
import bean.Author;
import bean.Topic;
import ejbInterface.NewsBeanRemote;
import entity.*;
import org.omg.CORBA.SystemException;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * User: Jaime
 * Date: 14/11/2014 - 05:02
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NewsBean implements NewsBeanRemote {
	@PersistenceContext(name="jpaUnit")
	EntityManager entityMan;

	@Override
	public Article getArticle(int id) {
		ArticleEntity articleEntity = entityMan.find(ArticleEntity.class, id);
		if(articleEntity == null) return null;
		Article article = new Article();

		List<String> highlights = new LinkedList<>();
		List<Author> authors = new LinkedList<>();
		List<String> images = new LinkedList<>();
		List<String> videos = new LinkedList<>();

		for(HighlightEntity hitem : articleEntity.getHighlights()) highlights.add(hitem.getText());
		for(AuthorEntity hitem : articleEntity.getAuthors()) authors.add(new Author(hitem.getId(), hitem.getName()));
		for(MediaEntity item : articleEntity.getMedia()){
			if(item.isImage()) images.add(item.getUrl());
			else videos.add(item.getUrl());
		}

		article
				.setId(articleEntity.getId())
				.setTitle(articleEntity.getTitle())
				.setUrl(articleEntity.getUrl())
				.setDate(articleEntity.getDate())
				.setTopic(articleEntity.getTopic().getName())
				.setAuthors(authors)
				.setHighlights(highlights)
				.setImages(images)
				.setVideos(videos)
				.setBody(articleEntity.getBody());

		return article;
	}

	@Override
	public List<Topic> getTopics() {
		List<Topic> topics = new LinkedList<>();
		Query query = entityMan.createQuery("SELECT t FROM TopicEntity t ORDER BY t.name ASC");
		List<TopicEntity> topicEntityList = query.getResultList();
		if(topicEntityList==null || topicEntityList.isEmpty()) return null;
		topics.add(new Topic(-1, "All"));
		for(TopicEntity t : topicEntityList){
			String[] tmpList =  t.getName().split(" ");
			for(int i=0; i<tmpList.length; i++) tmpList[i] = tmpList[i].substring(0,1).toUpperCase().concat(tmpList[i].substring(1));
			topics.add(new Topic(t.getId(), String.join("", tmpList) ));
		}
		return topics;
	}

	@Override
	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	public void addArticle(Article article) throws SystemException {
		ArticleEntity articleEntity;
		articleEntity = new ArticleEntity();
		try {

			articleEntity.setUrl(article.getUrl());
			if (article.getTitle() != null) articleEntity.setTitle(article.getTitle());
			if (article.getDate() != null) articleEntity.setDate(article.getDate());
			if (article.getBody() != null) articleEntity.setBody(article.getBody());

			TopicEntity topicEntity = retrieveTopic(article.getTopic());
			if (topicEntity == null) {
				topicEntity = new TopicEntity();
				topicEntity.setName(article.getTopic());
				entityMan.persist(topicEntity);
			}
			articleEntity.setTopic(topicEntity);

			// Authors
			if (article.getAuthors() != null) {
				Set<AuthorEntity> authorList = new HashSet<>();
				for (Author at : article.getAuthors()) {
					AuthorEntity authorEntity = retrieveAuthor(at.getName());
					if (authorEntity == null) {
						authorEntity = new AuthorEntity();
						authorEntity.setName(at.getName());
						entityMan.persist(authorEntity);
					}
					authorList.add(authorEntity);
				}
				articleEntity.setAuthors(authorList);
			}

			// Highlights
			if (article.getHighlights() != null) {
				for (String hg : article.getHighlights()) {
					HighlightEntity highlightEntity = new HighlightEntity();
					highlightEntity.setText(hg);
					highlightEntity.setArticle(articleEntity);
					entityMan.persist(highlightEntity);
				}
			}

			// Images
			if (article.getImages() != null) {
				for (String im : article.getImages()) {
					MediaEntity mediaEntity = new MediaEntity();
					mediaEntity.setUrl(im).setImage(true);
					mediaEntity.setArticle(articleEntity);
					entityMan.persist(mediaEntity);
				}
			}

			// Videos
			if (article.getVideos() != null) {
				for (String vd : article.getVideos()) {
					MediaEntity mediaEntity = new MediaEntity();
					mediaEntity.setUrl(vd).setImage(false);
					mediaEntity.setArticle(articleEntity);
					entityMan.persist(mediaEntity);
				}
			}
			entityMan.persist(articleEntity);
		} catch (Exception e) {
			System.out.println("#>Ignoring duplicated article.");
		}
	}

	private Article articleEntityToBean(ArticleEntity entity){
		Article article = new Article();
		String img = null;
		if(entity.getMedia()!=null){
			for(MediaEntity m : entity.getMedia()){
				if(m.isImage()){
					img = m.getUrl();
					break;
				}
			}
		}
		article
				.setId(entity.getId())
				.setTitle(entity.getTitle())
				.setTopic(entity.getTopic().getName())
				.setDate(entity.getDate())
				.setThumb(img);
		return article;
	}

	@Override
	public List<Author> getAuthors() {
		List<Author> authors = new LinkedList<>();
		Query query = entityMan.createQuery("SELECT a FROM AuthorEntity a ORDER BY a.name ASC");
		List<AuthorEntity> authorEntityList = query.getResultList();
		if(authorEntityList==null || authorEntityList.isEmpty()) return null;
		for(AuthorEntity a : authorEntityList){
			authors.add(new Author(a.getId(), a.getName()));
		}
		return authors;
	}

	@Override
	public PaginatedList getSearchPage(Integer topicId, Integer authorId, Date dateLimit, String textSearch, int pageNumber, int pageSize) {
		//Create list of results and paginatedList wrapper
		PaginatedList plist = new PaginatedList();
		List<Article> list = new LinkedList<>();
		List<ArticleEntity> entityList = null;
		Long count = null;
		Query query = null;
		Query cquery = null;

		if(topicId!=null){
			plist.setType(PaginatedList.QueryType.TOPIC);
			query = entityMan.createQuery("SELECT a FROM ArticleEntity a WHERE a.topic.id = :topicId ORDER BY a.date DESC");
			cquery = entityMan.createQuery("SELECT COUNT(a.id) FROM ArticleEntity a WHERE a.topic.id = :topicId");
			query.setParameter("topicId", topicId);
			cquery.setParameter("topicId", topicId);
		}else
		if(authorId!=null) {
			plist.setType(PaginatedList.QueryType.AUTHOR);
			query = entityMan.createQuery("SELECT DISTINCT a from ArticleEntity a JOIN a.authors t where t.id = :id ORDER BY a.date DESC");
			cquery = entityMan.createQuery("SELECT COUNT( DISTINCT a ) from ArticleEntity a JOIN a.authors t where t.id = :id");
			query.setParameter("id", authorId);
			cquery.setParameter("id", authorId);
		}else
		if(dateLimit!=null){
			plist.setType(PaginatedList.QueryType.DATE);
			query = entityMan.createQuery("SELECT a from ArticleEntity a WHERE a.date > :text ORDER BY a.date");
			cquery = entityMan.createQuery("SELECT COUNT(a) from ArticleEntity a WHERE a.date > :text");
			query.setParameter("text", dateLimit);
			cquery.setParameter("text", dateLimit);

		}else
		if(textSearch!=null){
			textSearch = textSearch.replace("%", "").replace(" ", "").trim();
			plist.setType(PaginatedList.QueryType.TEXT_SEARCH);
			query = entityMan.createQuery("SELECT DISTINCT a from ArticleEntity a JOIN a.highlights h WHERE h.text LIKE :query ORDER BY a.date DESC, a.topic.name DESC");
			cquery = entityMan.createQuery("SELECT COUNT( DISTINCT a ) from ArticleEntity a JOIN a.highlights h WHERE h.text LIKE :query");
			query.setParameter("query", "%" + textSearch + "%");
			cquery.setParameter("query", "%" + textSearch + "%");

		}else{
			plist.setType(PaginatedList.QueryType.NORMAL);
			query = entityMan.createQuery("SELECT a FROM ArticleEntity a ORDER BY a.date DESC");
			cquery = entityMan.createQuery("SELECT COUNT(a.id) FROM ArticleEntity a");
		}


		if(query!=null && cquery!=null){
			query.setMaxResults(pageSize);
			query.setFirstResult( (pageNumber-1) * pageSize );
			entityList = query.getResultList();
			count = (Long) cquery.getSingleResult();
		}

		if(entityList!=null) for(ArticleEntity a : entityList) list.add(articleEntityToBean(a));

		plist
				.setPage(list)
				.setTotal( (count!=null)?count:0 );

		return plist;
	}

	@TransactionAttribute(value=TransactionAttributeType.MANDATORY )
	private TopicEntity retrieveTopic (String topic) {
		String query = "SELECT t FROM TopicEntity t WHERE t.name = :topic";
		try {return (TopicEntity) entityMan.createQuery(query).setParameter("topic", topic).getSingleResult();
		} catch (NoResultException e ) { return null; }
	}

	@TransactionAttribute(value=TransactionAttributeType.MANDATORY )
	private AuthorEntity retrieveAuthor (String author) {
		String query = "SELECT a FROM AuthorEntity a WHERE a.name = :author";
		try {return (AuthorEntity) entityMan.createQuery(query).setParameter("author", author).getSingleResult();
		} catch (NoResultException e ) { return null; }

	}

}
