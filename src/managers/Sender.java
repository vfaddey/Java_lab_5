package managers;

import exceptions.NullUserRequestException;
import exceptions.WrongParameterException;
import model.Address;
import model.Coordinates;
import model.Location;
import model.OrganizationType;

public class Sender {
    private final ConsoleHandler consoleHandler;

    public Sender(ConsoleHandler consoleHandler) {
        this.consoleHandler = consoleHandler;
    }

    public String nameRequest() {
        String name = consoleHandler.ask("Введите название организации: ");
        try {
            if (Validator.isValidName(name)) {
                return name;
            } else {
                throw new WrongParameterException("Имя не может быть пустым");
            }
        } catch (WrongParameterException e) {
            consoleHandler.printError(e.toString());
            return nameRequest();
        }
    }

    public Coordinates coordinatesRequest() {
        String response = consoleHandler.ask("Введите через пробел координаты x и y (числа целые): ");
        int x;
        long y;
        try {
            if (response.split(" ").length < 2) {
                throw new WrongParameterException("Введены не все параметры.");
            }
            if (Validator.isCorrectNumber(response.split(" ")[0], Integer.class) && Validator.isCorrectNumber(response.split(" ")[1], Long.class)) {
                if (!Validator.isNull(response.split(" ")[0])) {
                    x = Integer.parseInt(response.split(" ")[0]);
                    y = Long.parseLong(response.split(" ")[1]);
                    return new Coordinates(x,y);
                } else {
                    throw new WrongParameterException("x не может быть null.");
                }
            } else {
                throw new WrongParameterException("Неверно введены числа.");
            }
        } catch (WrongParameterException e) {
            consoleHandler.printError(e.toString());
            return coordinatesRequest();
        }

    }

    public long annualTurnoverRequest() {
        long result = -1;
        String response = consoleHandler.ask("Введите годовой оборот компании (целое число): ");

        try {
            if (Validator.isNull(response) || Validator.isEmptyArray(response.split(" "))) {
                throw new NullUserRequestException("Введена пустая строка");
            }
            if (response.contains(" ")) {
                String[] splitted = response.split(" ");
                if (Validator.isCorrectNumber(splitted[0], Long.class)) {
                    result = Long.parseLong(splitted[0]);
                }
            } else if (Validator.isCorrectNumber(response, Long.class)) {
                result = Long.parseLong(response);
            } else {
                throw new WrongParameterException("Неверно введено число.");
            }
            if (result > 0) {
                return result;
            } else {
                throw new WrongParameterException("Годовой оборот не может быть меньше нуля.");
            }
        } catch (WrongParameterException | NullUserRequestException e) {
            consoleHandler.printError(e.toString());
            return annualTurnoverRequest();
        }
    }

    public int employeesCountRequest()  {
        int result = -1;
        String response = consoleHandler.ask("Введите количество сотрудников: ");

        try {
            if (Validator.isNull(response) || Validator.isEmptyArray(response.split(" "))) {
                throw new NullUserRequestException("Введена пустая строка");
            }
            if (response.contains(" ")) {
                String[] splitted = response.split(" ");
                if (Validator.isCorrectNumber(splitted[0], Integer.class)) {
                    result = Integer.parseInt(splitted[0]);
                }
            } else if (Validator.isCorrectNumber(response, Integer.class)) {
                result = Integer.parseInt(response);
            } else {
                throw new WrongParameterException("Неверно введено число.");
            }
            if (result > 0) {
                return result;
            } else {
                throw new WrongParameterException("Число сотрудников не может быть меньше одного.");
            }
        } catch (WrongParameterException | NullUserRequestException e) {
            consoleHandler.printError(e.toString());
            return employeesCountRequest();
        }
    }


    public OrganizationType organizationTypeRequest() {
        String response = consoleHandler.askOrganizationType(OrganizationType.values());
        String num;
        try {
            if (Validator.isNull(response) || Validator.isEmptyArray(response.split(" "))) {
                throw new NullUserRequestException("Поле не может быть пустым.");
            }
            if (response.contains(" ")) {
                num = response.split(" ")[0];
                if (Validator.isNull(num)) {
                    throw new WrongParameterException("Строка введена неверно.");
                }

            } else {
                num = response;
            }
            if (Validator.isCorrectNumber(num, Integer.class)) {
                if (Integer.parseInt(num) <= OrganizationType.values().length && Integer.parseInt(num) >= 1) {
                    return OrganizationType.values()[Integer.parseInt(num)-1];
                } else {
                    throw new WrongParameterException("Введено неверный номер.");
                }
            } else {
                throw new WrongParameterException("Неправильно введено число.");
            }

        } catch (WrongParameterException | NumberFormatException | NullUserRequestException e) {
            consoleHandler.printError(e.toString());
            return organizationTypeRequest();
        }
    }

    public Address officialAddressRequest() {
        String zipCode = consoleHandler.ask("Введите город(?): ");
        String loc = consoleHandler.ask("Введите координаты локации x, y, z через пробел (x и y - вещественные, z - целое): ");
        try {
            if (loc.split(" ").length < 3) {
                throw new WrongParameterException("Неверно введены координаты локации");
            }
            if (Validator.isStringWithNumbers(loc) && Validator.isValidName(zipCode)) {
                if (Validator.areCorrectLocationParams(loc.split(" ")[0], loc.split(" ")[1], loc.split(" ")[2])) {
                    double x = Double.parseDouble(loc.split(" ")[0]);
                    double y = Double.parseDouble(loc.split(" ")[1]);
                    long z = Long.parseLong(loc.split(" ")[2]);
                    return new Address(zipCode, new Location(x,y,z));
                } else {
                    throw new WrongParameterException("Неверно введены координаты локации.");
                }
            } throw new WrongParameterException("Неверно введены параметры. Попробуйте снова.");
        } catch (WrongParameterException e) {
            consoleHandler.printError(e.toString());
            return officialAddressRequest();
        }
    }

    public ConsoleHandler getConsoleHandler() {
        return consoleHandler;
    }
}
