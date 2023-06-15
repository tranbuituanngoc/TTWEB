package bean;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public abstract class AbBean  {

    public abstract boolean insert(Jdbi db);
    public abstract boolean delete(Jdbi db);
    public abstract boolean update(Jdbi db);

}