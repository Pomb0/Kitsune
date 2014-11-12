package ejbInterface;
import javax.ejb.Remote;

@Remote
public interface AddBeanRemote {
    public int add(int a, int b);
}