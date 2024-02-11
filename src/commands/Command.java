package commands;

import exceptions.WrongParameterException;
import managers.CollectionManager;

public abstract class Command {
    private final String nameInConsole;
    private final String description;
    protected String successPhrase;
    protected CollectionManager collectionManager;

    public Command(String nameInConsole, String description) {
        this.nameInConsole = nameInConsole;
        this.description = description;
    }

    public Command(String nameInConsole, String description, String successPhrase) {
        this.nameInConsole = nameInConsole;
        this.description = description;
        this.successPhrase = successPhrase;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    protected void printSuccess() {
        collectionManager.getConsoleHandler().println(successPhrase);
    }

    public String getNameInConsole() {
        return nameInConsole;
    }

    public String getDescription() {
        return description;
    }

    public void execute() throws WrongParameterException {

    }

}
