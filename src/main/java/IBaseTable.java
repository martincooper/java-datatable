
/**
 * IBaseTable. Specifying common functionality between DataTable and DataView.
 * Created by Martin Cooper on 19/07/2017.
 */
public interface IBaseTable {

    String name();
    DataColumnCollection columns();
    DataRowCollection rows();
    DataTable table();
    Integer rowCount();

    DataTable toDataTable();
    DataView toDataView();
}
