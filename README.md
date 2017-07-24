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

### Credits

Java DataTable is maintained by Martin Cooper : Copyright (c) 2017

## License

[Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)