package py.com.library.interfaces;

/**
 * @author Francesco Cannizzaro (fcannizzaro).
 */
public interface Stepable {

    void onPrevious();

    void onNext();

    void onError();

    void onUpdate();

}
