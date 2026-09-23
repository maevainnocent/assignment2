# Design Discussion — Assignment #3

## How Assignment #2 was organized

Assignment #2 was a single class, `ETLPipeline`, with one long `main`
method. That one method opened the input file, read the header, looped
over every line, split and validated each field, computed gross pay
(including the overtime and IT-bonus rules), classified pay level and
employment status, formatted the output row, and wrote it — all in a
single block of procedural code. There was no encapsulation: an
employee's data existed only as local variables and array elements
inside the loop, and the file-format details, validation rules, and
payroll business rules were all interleaved with no clear boundaries
between them.

## Design changes made for Assignment #3

Assignment #3 splits that one method into classes based on the three
natural stages of an ETL pipeline — **Extract**, **Transform**, and
**Load** — plus the data each stage produces and consumes:

| Class | Responsibility |
|---|---|
| `Employee` | Holds one validated row of *raw* input data (ID, name, department, hours, rate). |
| `EmployeeParser` | Turns one CSV line into an `Employee`, applying every validation rule (field count, numeric parsing, non-negative hours/rate). |
| `InvalidEmployeeRecordException` | Signals that a line failed validation, so the reader doesn't need to inspect return values to know a row was bad. |
| `EmployeeCsvReader` | Extract step: reads the input file line by line, delegates parsing to `EmployeeParser`, and tracks rows-read / rows-skipped. |
| `PayrollCalculator` | Transform step: applies every payroll rule (overtime, IT bonus, pay-level classification, employment-status classification) to an `Employee` and produces a `TransformedEmployee`. |
| `TransformedEmployee` | Holds one fully-computed output row and knows how to format itself as CSV. |
| `EmployeeCsvWriter` | Load step: writes a header and a list of `TransformedEmployee` rows to the output file. |
| `ETLPipeline` | Orchestrates the three steps in `main` and prints the summary counts. It contains no file-format or business-rule logic of its own. |

## Why these abstractions

- **`Employee` vs. `TransformedEmployee`** are kept as two separate
  classes instead of one mutable class, because they represent two
  distinct states of the data: raw-but-valid input, and fully computed
  output. Splitting them avoids a class with a confusing "half-filled-in"
  state partway through the pipeline.
- **`EmployeeParser` is separate from `EmployeeCsvReader`** so that
  "what makes a row valid" (a business/data rule) is not tangled up
  with "how to read a file" (an I/O concern). The reader can be tested
  or reused without caring about validation details, and vice versa.
- **`PayrollCalculator` isolates every dollar-and-cents rule** (overtime,
  the IT bonus, the pay-level thresholds, the part-time cutoff) in one
  place, as named constants and small private methods, instead of as
  inline literals buried inside a 150-line method.
- **`InvalidEmployeeRecordException`** replaces the original approach of
  silently `continue`-ing past bad rows with an explicit signal that a
  caller must handle, making the "skip invalid rows" behavior visible in
  the method signatures rather than implicit in control flow.

## How responsibilities were divided differently

In Assignment #2, one method was responsible for I/O, validation,
business rules, and formatting simultaneously — a change to any one of
those (e.g., a new pay-level threshold) required editing the same block
of code as file-reading logic. In Assignment #3, each responsibility
has exactly one class: `EmployeeCsvReader` only knows about reading
files, `PayrollCalculator` only knows about payroll math, and
`EmployeeCsvWriter` only knows about writing files. `ETLPipeline` itself
no longer contains any business logic — it only wires the three
collaborators together.

## Why this is an improvement

- **Single Responsibility**: each class has one reason to change. A new
  payroll rule only touches `PayrollCalculator`; a new output file
  format only touches `EmployeeCsvWriter`.
- **Encapsulation**: employee data and payroll results are objects with
  meaningful names and getters, not loose local variables scattered
  through a loop.
- **Testability**: `EmployeeParser` and `PayrollCalculator` can each be
  tested in isolation (e.g., "does 45 hours at $30/hr produce
  $1,496.25?") without needing to read or write any files.
- **Readability**: `ETLPipeline.main` now reads as a three-line summary
  of the pipeline (read → transform → write), instead of a 150-line
  method mixing every concern together.

The functional behavior is unchanged: the same CSV rows are read, the
same rows are skipped for the same reasons, and the same output CSV and
console summary are produced.

## AI and Internet Resources

Claude (Anthropic) was used to help design the class breakdown described
above and to draft this document and the accompanying Java source files.
Transcript: *[https://claude.ai/share/7c76c9af-6c3e-481d-8752-ec5c485fc504]*.

No other Internet resources were used.
