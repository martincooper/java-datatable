#Java DataTable

## Overview

Java DataTable is a lightweight, in-memory table structure written in Java. The implementation is entirely immutable.
Modifying any part of the table, adding or removing columns, rows, or individual field values will create and return a
new structure, leaving the old one completely untouched. This is quite efficient due to structural sharing.

### Features :
 * Fully immutable implementation.
 * All changes use structural sharing for performance.
 * Table columns can be added, inserted, updated and removed.
 * Rows can be added, inserted, updated and removed.
 * Individual cell values can be updated.
 * Any inserts, updates or deletes keep the original structure and data completely unchanged.
 * Internal type checks and bounds checks to ensure data integrity.
 * RowData object allowing typed or untyped data access.
 * Full filtering and searching on row data.
 * Single and multi column quick sorting.
 * DataViews to store sets of filtered / sorted data.

## Implementation

The main focus for this project was to have the flexibility of a standard mutable table with all the common requirements,
add / remove columns, add / remove rows, update cells and values in individual cells, but with the benefit of immutable
and persistent data structures.

It allows access to the table data in a row / column format where the column data types may, or may not be known at
design time, for example a table read from a database, a CSV file or other dynamic data source.

Internally the data is stored as a collection of immutable Vectors ensuring type information is fully preserved, with
checks ensuring full type integrity is maintained at runtime. The Vector implementation used is based on a `bit-mapped trie` 
and provided by the excellent functional Java library [VAVR](http://www.vavr.io/).

The table data can be easily accessed, filtered and modified through a RowData object, providing a range of typed and
untyped methods depending on how much type info is known at design time.

Most methods have both checked and unchecked versions. The checked ones perform additional bounds checking and return
results as a Try<T> with detailed error information. The unchecked ones will just return a <T> and throw an exception
on any out of bounds errors but with potentially faster access on a significantly large amount of updates.

# Example Usage

## Creating DataTables
To create a new DataTable, create the DataColumns required (just specify a unique column name and
the data populating each one) as shown below.

```java
// Example of building up a DataTable manually.
private Try<DataTable> createDataTableManually() {
    
    // Create some test example data for the columns.
    List<String> stringData = List.of("AA", "BB", "CC", "DD");
    List<Integer> intData = List.of(3, 5, 7, 9);
    List<Boolean> boolData = List.of(true, false, true, false);
    
    // Create each column, with unique name, adding any data.
    IDataColumn stringCol = new DataColumn<>(String.class, "StrCol", stringData);
    IDataColumn intCol = new DataColumn<>(Integer.class, "IntCol", intData);
    IDataColumn boolCol = new DataColumn<>(Boolean.class, "BoolCol", boolData);
    
    // Create the new table.
    IDataColumn[] cols = { stringCol, intCol, boolCol };
    return DataTable.build("NewTable", cols);
    
  // If any of the columns fail validation (duplicate column names, or columns contain
  // data of different lengths), then it'll return a Failure. Else Success<DataTable>
}
```

There is also a DataTable builder object, which makes creating a new DataTable, populated with values much simpler.

```java
// Example of building up a DataTable using the provided builder.
private Try<DataTable> createDataTableUsingBuilder() {
    
    return DataTableBuilder
            .create("NewTable")
            .withColumn(String.class, "StrCol", List.of("AA", "BB", "CC", "DD"))
            .withColumn(Integer.class, "IntCol", List.of(3, 5, 9, 11))
            .withColumn(Boolean.class, "BoolCol", List.of(true, false, true, false))
            .build();
}
```

## Adding Columns
To add a new Column, create a new DataColumn and call the add method on the table.columns
collection. This will return a new DataTable structure including the additional column.

```java
// Example of adding a new column to an existing table.
private Try<DataTable> addNewColumn(DataTable existingTable) {
    
    // First create the new column with a unique name and some data.
    IDataColumn intCol = new DataColumn<>(Integer.class, "IntCol", List.of(3, 5, 7, 9));
    
    // Call table.columns().add() to return a new Try<DataTable> structure containing the additional column.
    return existingTable.columns().add(newCol);
    
    // If adding the new column fails validation (duplicate column names, or columns
    // contain data of different lengths), then it'll return a Failure. Else Success<DataTable>
}
```

## Removing Columns
To remove a Column, call the remove method on the table.columns collection.
This will return a new DataTable structure with the column removed.

```java
// Example of removing an existing column from a table.
private Try<DataTable> removeColumn(DataTable existingTable) {
    
    // Call table.columns().remove() to return a new Try<DataTable> structure with the column removed.
    return existingTable.columns().remove("ColNameToRemove");
    
  // If removing the column fails validation (column name not found),
  // then it'll return a Failure. Else Success<DataTable>
}
```

## Row / Data Filtering
Access to the underlying data in the table the DataRow object can be used. This allows either typed or
untyped access depending if type info is known at design time. The DataTable object implements a filter
method where you can provide row criteria. The results are returned in a DataView 
object which is a view on the underlying table. To filter a table this can be done as follows...

```java
// Example of filtering a DataTable.
private DataView filterTable(DataTable existingTable) {
    
    // Start with a DataTable with some data.
    DataTable table = DataTableBuilder
            .create("NewTable")
            .withColumn(String.class, "StrCol", "AA", "BB", "CC", "DD")
            .withColumn(Integer.class, "IntCol", 10000, 1000, 100, 10)
            .withColumn(Double.class, "DoubleCol", 1.1, 5.5, 10.5, 100.5)
            .withColumn(Boolean.class, "BoolCol", true, true, false, false)
            .build().get();
    
    // Filter the table, only returning where IntCol values > 100 and DoubleCol values are < 10
    DataView view = table.filter(row ->
            row.getAs(Integer.class, "IntCol") > 100 &&
            row.getAs(Double.class, "DoubleCol") < 10);
    
    return view;
}
```

## Row / Data Access
DataRow has a number of ways to access the underlying data, depending on the amount of information
known at design time about the data and it's type. The simplest way with no type information is
calling .data() on the DataRow item. This returns an Object[] of all the values.

```java
private Object[] getRowValues(DataTable table, Integer rowIdx) {
    
    // Returns an object array of all the values for the specified row.
    return table.rows().get(rowIdx).data();
}
```

The DataRow has additional type checked and bounds checked methods allowing safer and
more composable access to the underlying data.

```java
private void typedAndCheckedDataAccess(DataRow dataRow) {
    
  // Each .tryGetAs<T> is type checked and bounds / column name checked so can be composed safely
  // Accessed by column name...
  Try<Integer> itemData = row.tryGetAs(Integer.class, "IntegerCol");
  
  // Or by column index.
  Try<Integer> otherData = row.tryGetAs(Integer.class, 1);
}
```

### Credits

Java DataTable is maintained by Martin Cooper : Copyright (c) 2017

## License

[Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)