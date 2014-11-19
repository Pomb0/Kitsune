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
import javax.persistence.criteria.*;
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

		//Create CriteriaBuilder
		CriteriaBuilder criteriaBuilder = entityMan.getCriteriaBuilder();

		//Create Query To Count Results
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(ArticleEntity.class)));

		//Create Query to get Articles
		CriteriaQuery<ArticleEntity> cquery = criteriaBuilder.createQuery(ArticleEntity.class);

		//Define root to get ArticleEntities
		Root<ArticleEntity> c = cquery.from(ArticleEntity.class);

		//Create Possible Parameters
		ParameterExpression<Integer> topicParam = criteriaBuilder.parameter(Integer.class);
		ParameterExpression<Integer> authorParam = criteriaBuilder.parameter(Integer.class);
		ParameterExpression<Date> dateParam = criteriaBuilder.parameter(Date.class);
		ParameterExpression<String> queryParam = criteriaBuilder.parameter(String.class);


		Expression where = null;
		List<Predicate> predicates = new LinkedList<>();

		if(topicId!=null && topicId != -1) predicates.add(criteriaBuilder.equal(c.get("topic").get("id"), topicParam));
		if(authorId!=null && authorId != -1) {
			predicates.add(criteriaBuilder.isMember( new AuthorEntity(), c.get("authors") ));
		}

		where = criteriaBuilder.and(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		//Set Where
	   	if(where!=null){
		    countQuery.where(where);
		    cquery.where(where);
	    }

		cquery.orderBy(criteriaBuilder.desc(c.get("date")));
		Query query = entityMan.createQuery(cquery);
		Query countQ = entityMan.createQuery(countQuery);

		//Set TopicId
		if(topicId!=null && topicId != -1){
			query.setParameter(topicParam, topicId);
			countQ.setParameter(topicParam, topicId);
		}

		//Set AuthorId
		if(authorId!=null && authorId != -1){
			query.setParameter(authorParam, authorId);
			countQ.setParameter(authorParam, authorId);
		}


		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		Long total = (Long) countQ.getSingleResult();
		List <ArticleEntity> articleEntities = (List <ArticleEntity>)query.getResultList();

		for(ArticleEntity articleEntity : articleEntities) list.add(articleEntityToBean(articleEntity));

		plist
				.setPage(list)
				.setTotal(total);
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
