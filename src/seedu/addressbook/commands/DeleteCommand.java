package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index or name from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "OR\n"
            + "deletes the person identified by their name.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private String name;

    public DeleteCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    public DeleteCommand(String name) {this.name = name;}

    @Override
    public CommandResult execute() {
        try {
            ReadOnlyPerson target = null;
            if (name == null) {
                target = getTargetPerson();
            } else {
                for (ReadOnlyPerson person : addressBook.getAllPersons()) {
                    if (name.equals(person.getName())) {
                        target = person;
                        break;
                    }
                }
            }
            addressBook.removePerson(target);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, target));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

}
