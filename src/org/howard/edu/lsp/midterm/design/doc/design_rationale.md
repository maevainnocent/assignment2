# Evolving Requirements and Design Rationale

## How the design accommodates the new requirements

**Storage (file or database).** `RegistrationService` depends only on the `RegistrationRepository` interface and never on a concrete storage class. To support a database, I add a new class, `DatabaseRegistrationRepository`, which implements the same interface. `RegistrationService`, `Registration`, and `TuitionCalculator` are unchanged. The deployment environment decides which implementation to supply when the service is created, for example from a configuration setting.

**Confirmations (email or text).** The same approach applies to notification. `RegistrationService` depends on the `ConfirmationNotifier` interface. `EmailNotifier` already exists, and I add `SmsNotifier`, which implements the same interface and uses the student's phone number. No existing class needs edits. This follows the open-closed principle: the system is extended by adding classes, not by changing working code.

## Changes to the CRC cards

- Add the card **DatabaseRegistrationRepository** (implements RegistrationRepository): saves registrations to a database. Collaborator: Registration.
- Add the card **SmsNotifier** (implements ConfirmationNotifier): delivers confirmations by text message to the student's phone number. Collaborators: Registration, Student.
- Add a card **RegistrationServiceFactory**: reads deployment configuration and assembles a `RegistrationService` with the right repository and notifier. Collaborators: RegistrationService, RegistrationRepository, ConfirmationNotifier.
- `Student` already holds a phone number, so it needs no change. If different students prefer different channels, `Student` would also store a preferred contact method (see below).

## Key decision: who owns which responsibility

The most important decision is that **`RegistrationService` coordinates the workflow but does not choose or construct its collaborators**. Choosing a concrete repository or notifier is the job of the factory, which reads the deployment configuration.

I made this choice for three reasons:

1. **Single reason to change.** If the service constructed the concrete classes itself, it would have to be edited every time a new storage or notification option was added. With the factory, the service changes only when the registration workflow changes.
2. **Testability.** The service can be tested with simple fake implementations of the two interfaces, without touching a real file, database, or messaging system.
3. **Consistency with Part 2.** In my Part 2 design the service already collaborates with the interfaces, not the concrete classes, so this decision is the same principle applied one step further, and it follows Riel's guidance to minimize coupling and keep a class focused on one key abstraction.

I deliberately did not put the delivery-method choice inside `Student` or `Registration`. Those classes should store data, such as a preferred contact method, but should not know how to send a message or write a record. Those behaviors belong to the notifier and repository classes.
