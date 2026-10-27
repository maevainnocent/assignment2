# Design Evaluation of `CourseRegistration`

`CourseRegistration` works for a single case, but its design has several problems. The main ones are listed below, each tied to Arthur Riel's heuristics.

## 1. Public data (Heuristic 2.1: all data should be hidden within its class)
All five fields (`studentId`, `studentName`, `email`, `courseCode`, `creditHours`) are `public`. Any class can change them without validation, so a registration can hold a negative `creditHours` or a malformed email. Because outside code can depend directly on the field layout, the representation can never change safely.

## 2. God class with too many unrelated responsibilities (Riel: do not create god classes; a class should capture one and only one key abstraction, Heuristic 2.8)
`registerStudent()` does six jobs: tuition calculation, discount policy, console output, file persistence, email notification, and logging. These are separate concerns that change for different reasons. A new tuition rate, a new storage medium, and a new message format would each force edits to this same class. That makes it harder to read, test, and maintain.

## 3. Mixed abstractions: student data, registration data, and policy in one class (Riel Heuristic 2.8: a class should capture one and only one key abstraction)
The class stores student attributes (name, email) next to course attributes (`courseCode`, `creditHours`), so it is neither a clean Student nor a clean Registration. A student's name and email are duplicated in every registration instead of living in one `Student` object.

## 4. Business rules hard-coded as magic numbers (Riel: keep related data and behavior together; maintainability)
The rate of `750.0`, the threshold of `12`, and the multiplier of `0.90` are embedded in a method. A tuition policy change requires editing the registration workflow code, and the same rule cannot be reused or tested on its own.

## 5. Low cohesion and tight coupling to concrete implementations (Riel: distribute system intelligence horizontally and minimize coupling between classes)
The class is bound directly to `FileWriter`, `System.out`, and `Date`. Supporting a database or text-message confirmations would mean modifying this class, which violates the open-closed principle. Code that cannot swap collaborators also cannot be unit tested without touching the filesystem and console.

## 6. Fake behaviors and a method that only exists to run steps in order (Riel: behavior belongs in the class that owns the relevant data and abstraction)
"Sending confirmation email" is just a `println`, and no real sending takes place. The method looks complete but does nothing, which is a correctness risk. Behavior also lives in the wrong place: calculating tuition is a function of credit hours and policy, not something a registration should know how to do.

## 7. Poor error handling (correctness)
`catch (Exception e)` followed by `printStackTrace()` swallows the failure. If the file write fails, the method still prints a confirmation and sends the "email," so the student is told the registration succeeded when it was never saved. The `FileWriter` is also not closed on failure, so a resource may leak. A try-with-resources block and propagated exceptions would fix both.

## 8. Weak naming and no domain data model (clarity)
`registerStudent()` does not register anyone. It prints and writes a record. The class name and method name do not match what the code does, and the record is stored as an untyped comma-joined string, so there is no structure to read it back safely.

## Summary
The root cause is a violation of single responsibility and information hiding. Fixing it means separating the abstractions (Student, Registration, tuition policy, persistence, notification, logging), hiding data behind methods, and letting a coordinating class collaborate with those smaller classes through abstractions.
