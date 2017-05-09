package py.com.library.interfaces;


import java.util.List;

import py.com.library.AbstractStep;

/**
 * Created by Francesco Cannizzaro on 08/05/2016.
 */
public interface Pageable {

    void add(AbstractStep fragment);

    void set(List<AbstractStep> fragments);

}
