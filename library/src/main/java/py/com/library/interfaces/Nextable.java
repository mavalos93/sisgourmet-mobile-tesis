package py.com.library.interfaces;

/**
 * @author Francesco Cannizzaro (fcannizzaro).
 */
public interface Nextable {

    boolean nextIf();

    boolean isOptional();

    void onStepVisible();

    void onNext();

    void onPrevious();

    String optional();

    String error();

}