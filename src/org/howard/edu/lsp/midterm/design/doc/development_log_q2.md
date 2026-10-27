# Development Log


## Resources used
- **AI tool:** claude– Drafted the design docs and Identified the problems with the original class and redesigned it by separating responsibilities into different classes and using interfaces to make future changes easier.
- **Websites / documentation:** NONE
- **Offline sources (lecture notes, textbook):** I did not use the textbook but i did use some lecture notes

## Complete AI conversation transcript


Tool: Claude (claude.ai), conversation dated October 26, 2026.

Notes on format: "User" lines are my prompts. "Claude" blocks are Claude's chat replies, reproduced as written. Text in [brackets] describes tool actions Claude took between replies (reading the uploaded exam, writing and compiling files, building a doc); the resulting file contents are the submitted repository files and the shared doc.

---

## Turn 1

**User:** [Uploaded file: Mid-term.docx, no message]

**Claude:** [Read the uploaded exam document using file-extraction commands.]

I've read through **Mid-term.docx**. It's the CSCI 363 (Large Scale Programming) Fall 2026 midterm, worth 100 points:

- **Q1 (35 pts):** Implement `Book` and `Library` from CRC cards so they pass the provided Driver (44 checks). It also needs Javadoc, `crccards.md`, and a development log.
- **Q2 (25 pts):** Evaluate the design of `CourseRegistration` using Riel's heuristics, propose a CRC-card redesign, and explain how it handles new storage and notification options. It also needs a development log.
- **Q3 (30 pts):** Refactor `EmployeePayCalculator` into an inheritance hierarchy with polymorphism, including overtime for full-time employees, so it passes the Driver (12 checks). It also needs a development log.
- **Q4 (10 pts):** A personal reflection on using AI in this course.

You didn't say what you'd like me to do with it. The exam allows AI for Q1–3 as long as you disclose it, so I can help with those. I can't help with Q4, since the exam says it must be entirely your own writing with no AI involvement.

For Q1–3, the development logs require the complete AI conversation transcript, so you'd need to include this conversation in them.

What would you like to do? For example, I could work through one of the questions with you, explain a concept like CRC cards or Riel's heuristics, or review something you've already written.

---

## Turn 2

**User:** yes help me

**Claude:** [Created the repository folder structure; extracted the two provided Driver.java files from the exam document; wrote Book.java and Library.java and compiled and ran the provided Driver (44 PASSED, 0 FAILED); wrote Employee.java, FullTimeEmployee.java, PartTimeEmployee.java and Contractor.java and compiled and ran the provided Driver (12 PASSED, 0 FAILED); wrote crccards.md, evaluation.md, design.md, design_rationale.md; corrected unverifiable Riel heuristic numbers in evaluation.md; created development log templates; zipped the package as midterm_submission.zip.]

I've built the Q1–3 submission package in `midterm_submission.zip`, in the exact folder structure the exam requires. I compiled it against your provided Drivers. Q1 prints `44 PASSED, 0 FAILED`, and Q3 prints `12 PASSED, 0 FAILED`.

**Q1:** `Book.java` and `Library.java` are written with Javadoc, and `crccards.md` is included. `Library` uses a `TreeMap` keyed by ISBN, which gives fast lookup, one-step duplicate detection, and ISBN-sorted retrieval for free. It computes the borrowed count from the books' own state, so the count can't drift out of sync.

**Q2:** `evaluation.md`, `design.md` (the CRC cards), and `design_rationale.md` are included. The key decision in the rationale is that `RegistrationService` coordinates the workflow but doesn't choose or construct its storage and notification classes. A factory picks them, so adding a database or SMS later means adding classes, not editing existing ones.

**Q3:** `Employee` is an abstract base class with `FullTimeEmployee`, `PartTimeEmployee`, and `Contractor` overriding `calculatePay()`. Full-time overtime is 1.5× beyond 40 hours.

Some things you still need to do before you submit:
1. **Development logs:** I created `development_log.md`, `development_log_q2.md`, and `development_log_q3.md` as templates. You have to paste in the full transcript of this conversation, since the exam requires it. Q1's log must contain the transcript itself, not just a link.
2. **Author names:** The Javadoc says `@author Student Name`, so replace it with your own name.
3. **Understand the work:** Read through the solutions so you can explain them. Check `evaluation.md` against what your lectures covered on Riel's heuristics. I cited the ones I'm sure of by number and the rest by name.
4. **Push to GitHub:** Only repository contents at the end of the exam are graded.

**Q4** I've left alone. The exam requires it to be entirely your own writing, so there's an empty `reflection/doc` folder where `ai_reflection.md` goes.

---

## Turn 3

**User:** send me the midterm doc so I can copy it all

**Claude:** [Created a shared doc titled "CSCI 363 Midterm – Q1–Q3 Files (Copy-Ready)" containing every Q1–Q3 file in code blocks, filled in section by section.]

[CSCI 363 Midterm – Q1–3 Files (Copy-Ready)](https://claude.ai/artifact/HJDDDWANhX6ZWEgDkpB2AB) is ready, with every file in its own code block for copying. It doesn't include the two provided `Driver.java` files, so copy those from your exam as given.

---

## Turn 4

**User:** explain how you did 1 2 and 3

**Claude:** Here is the reasoning behind each question, so you can explain it yourself if asked.

## Question 1: Book and Library

I started from the CRC cards. Book owns one book's state, and Library owns rules that involve the whole collection. Every other decision follows from that split.

**Book** keeps its fields private, and `available` can only change through `borrowBook()` and `returnBook()`. Each method checks the current state first and throws `IllegalStateException` if the change isn't valid. This is how "availability cannot be modified directly by other classes" is satisfied.

**Library** uses a `TreeMap<String, Book>` keyed by ISBN, for three reasons:
- Lookup by ISBN is fast, and `findBook` is just a `get`.
- Duplicate detection is one `containsKey` check, and the original book is never overwritten.
- A `TreeMap` keeps keys in ascending String order, so `getBooksSortedByIsbn()` needs no sorting code. Insertion order doesn't matter because the map orders itself.

`borrowBook` follows the exam's ordering:
1. Look up the book, throwing `IllegalArgumentException` if it's missing.
2. Check the three-book limit, throwing `IllegalStateException` if it's reached.
3. Delegate to `book.borrowBook()`.

Both checks run before any state changes, so a failed operation changes nothing. Library enforces the limit because only Library can see all the books. Delegating the state change to Book follows the "locate the Book and delegate" rule.

I compute `getBorrowedBookCount()` by counting unavailable books instead of keeping a separate counter. A counter could drift out of sync if state changed elsewhere, but counting the real state can't.

## Question 2: Design evaluation

This is analysis, not code. I read `CourseRegistration` and asked what reasons it has to change.

**Evaluation:** The class has public fields (Riel 2.1) and does six unrelated jobs, which makes it a god class that breaks "one key abstraction" (Riel 2.8). It also hard-codes business rules, couples directly to `FileWriter` and `System.out`, and swallows exceptions, so it can report success after a failed save.

**Redesign:** Each responsibility became its own CRC card:
- `Student` holds the student's data.
- `Registration` holds the registration's data.
- `TuitionCalculator` owns the pricing rules.
- `RegistrationRepository` and `ConfirmationNotifier` are interfaces for storage and delivery.
- `RegistrationService` coordinates the workflow.

**Rationale:** Part 3 is the payoff of using interfaces. A database or SMS support means adding a new class that implements the existing interface, so nothing already working has to change. The one decision I justified in depth is that the service doesn't create its own collaborators. A factory chooses them from deployment configuration, so the service only changes when the workflow changes.

## Question 3: Inheritance and polymorphism

The original code has one method with an `if` chain on a type string. Refactoring means replacing that chain with a class hierarchy.

- `Employee` is abstract. It holds the shared data (hours and rate) and declares an abstract `calculatePay()`.
- `FullTimeEmployee`, `PartTimeEmployee`, and `Contractor` each override `calculatePay()` with their own rule.
- The Driver calls `employee.calculatePay()` on an `Employee` reference, and Java picks the right version at runtime. That is polymorphism, and the string comparison is gone.

I wrote the full-time overtime rule as regular hours (capped at 40) times the rate, plus overtime hours times the rate times 1.5. At 45 hours and a rate of 20, that gives 800 + 150 = 950, which matches the Driver. Part-time stays straight pay, since the Driver expects 45 hours at 20 to equal 900. Contractor pay is hours × rate × 1.15 with no overtime, which matches 1035.

## How I checked it

I extracted both Drivers from your docx and compiled and ran them against my code. Q1 printed 44 passed and 0 failed, and Q3 printed 12 passed and 0 failed. For Q2 there's no test, so I checked that the Part 3 answer is consistent with the Part 2 cards.

## Where to be careful

- Be ready to explain why `TreeMap` fits Q1 and why counting beats a counter.
- Check the Riel citations in `evaluation.md` against your lecture notes. I cited only 2.1 and 2.8 by number, so if your class used a different list, adjust them.
- Run the code yourself in your IDE before submitting, since I tested it in a different environment.

If you want, I can quiz you on these so you're ready to explain them.

---

## Turn 5

**User:** what are the checks you did for each question

**Claude:** Here is what I actually ran, and what I only reasoned through.

## Question 1

- **Compiled and ran the provided Driver.** I extracted `Driver.java` from your docx, compiled it with `Book` and `Library`, and ran it. The result was `44 PASSED, 0 FAILED` and a functional score of 25.00 / 25. This also confirms the package declarations match the folder structure, because the run used the full package name.
- **Counted the `crccards.md` sentences.** The file has five, within the required 3–5. It covers both required points: why Library collaborates with Book but not the reverse, and why Library enforces the limit.
- **Checked the zip contents.** I removed compiled `.class` files before zipping, and the file listing showed none.

I did not run the `javadoc` tool to confirm the comments generate cleanly. I only wrote class descriptions, `@param`, `@return`, and `@throws` tags by hand, so that part is unchecked by a tool.

## Question 2

There is nothing to run here, so the checks were manual:
- **Format check:** I confirmed each card in `design.md` uses the required Class / Responsibilities / Collaborators layout.
- **Consistency check:** I confirmed the Part 3 answer matches the Part 2 cards. The service depends on interfaces, `Student` already has a phone number for SMS, and the new cards (`DatabaseRegistrationRepository`, `SmsNotifier`, the factory) are listed as changes.
- **Citation fix:** I caught that my first draft of `evaluation.md` had Riel heuristic numbers I couldn't verify (like "5.x") and removed them. Only 2.1 and 2.8 remain as numbers, and the rest are cited by name.

I have no automated check for this question. Whether the content earns full credit depends on your instructor's expectations, so compare it to your lecture notes.

## Question 3

- **Ran the provided Driver.** I extracted it from your docx, compiled it with the four classes, and ran it. The result was `12 PASSED, 0 FAILED`.
- **Checked the arithmetic by hand.** Full-time at 45 hours and a rate of 20 gives 800 + 150 = 950, and contractor at 45 hours gives 1035, both matching the Driver's expected values.

The Driver only tests those 12 cases. I didn't write extra tests, for example fractional hours.

## Other limits

- I tested with Java 21 in my environment, not your IDE or Java version, so run it yourself before submitting.
- I rebuilt the Driver files from your docx by script, dropping blank lines. They compile and run, but the formatting may differ from the original. Copy the Drivers from your exam as given, not from my package.
- I didn't read the Claude Doc back after writing it. The code in it is the same text I compiled, but check that the copy-paste preserved it. A quick compile after pasting is the best check.

---

## Turn 6

**User:** before I move forward what do I need to do

**Claude:** Here's what's left, in order.

1. **Set up the repo.** Create the folder structure from the exam under `org/howard/edu/lsp/midterm/` and copy each file from the doc into the right place. If you'd rather not copy by hand, `midterm_submission.zip` has the same files already laid out.

2. **Use your own Drivers.** Copy both `Driver.java` files from your exam unmodified into `crccards/` and `inheritance/`. The ones in my zip were rebuilt from the docx by a script, so don't submit those.

3. **Put your name in.** Replace `Student Name` in every `@author` tag (Book, Library, Employee, FullTimeEmployee, PartTimeEmployee, Contractor).

4. **Compile and run in your own IDE.** Both Drivers should print `44 PASSED, 0 FAILED` (Q1) and `12 PASSED, 0 FAILED` (Q3). This also catches any copy-paste problems.

5. **Write the three development logs.** They go in `crccards/doc/development_log.md`, `design/doc/development_log_q2.md`, and `inheritance/doc/development_log_q3.md`. Each needs your real disclosure plus the full transcript of this conversation, with every prompt and response. Q1's log requires the transcript itself, not just a link. For Q2 and Q3 a working shareable link is allowed. If you decide you used no outside help, the exam's exact wording applies, but that wouldn't be accurate now.

6. **Write Q4 yourself.** The file is `reflection/doc/ai_reflection.md`. The exam forbids AI for any part of it, so I can't draft or edit it. It needs a specific example of AI helping, a specific example of AI being wrong or a limitation you've seen, and how you'll use AI going forward while building your own skills.

7. **Read the solutions until you can explain them.** My earlier explanation covers the reasoning for each question. Check the Riel heuristics in `evaluation.md` against your lecture notes.

8. **Push to GitHub before the exam ends.** Only what's in the repo at the end is graded. Make sure no `.class` files or IDE project files get committed.

Do step 5 last for the transcript, so it includes everything up to your final submission.

---

## Turn 7

**User:** can you give me out script

**Claude:** [Wrote this transcript file and the three development log files containing it.]
