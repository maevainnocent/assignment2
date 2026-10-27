# Improved Design: CRC Cards

Class: Student
Responsibilities:
- Store student identity (ID, name) and contact information (email, phone)
- Provide contact details to collaborators that need them
Collaborators:
- None

Class: Registration
Responsibilities:
- Store the facts of one registration: which student, which course, and credit hours
- Validate its own data (for example, credit hours must be positive)
- Hold the computed tuition amount once assigned
Collaborators:
- Student

Class: TuitionCalculator
Responsibilities:
- Own the tuition policy (per-credit rate, heavy-load discount and its threshold)
- Calculate the tuition for a given number of credit hours
Collaborators:
- None

Class: RegistrationService
Responsibilities:
- Coordinate the registration workflow from start to finish
- Ask TuitionCalculator for the tuition and record it on the Registration
- Ask the repository to save the registration
- Ask the notifier to send confirmation only after saving succeeds
- Ask the logger to record the event
Collaborators:
- Registration, TuitionCalculator, RegistrationRepository, ConfirmationNotifier, RegistrationLogger

Class: RegistrationRepository (interface)
Responsibilities:
- Define how a registration is saved (and loaded)
- Hide the storage medium from the rest of the system
Collaborators:
- Registration

Class: FileRegistrationRepository (implements RegistrationRepository)
Responsibilities:
- Save registrations to a local file, closing resources and reporting failures
Collaborators:
- Registration

Class: ConfirmationNotifier (interface)
Responsibilities:
- Define how a registration confirmation is delivered
Collaborators:
- Registration, Student

Class: EmailNotifier (implements ConfirmationNotifier)
Responsibilities:
- Deliver confirmations to the student's email address
Collaborators:
- Registration, Student

Class: RegistrationLogger
Responsibilities:
- Record that a registration was processed, with a timestamp
Collaborators:
- Registration
