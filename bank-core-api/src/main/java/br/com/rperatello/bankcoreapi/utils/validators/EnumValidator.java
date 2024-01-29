package br.com.rperatello.bankcoreapi.utils.validators;

public class EnumValidator {	
    
    public static <E extends Enum<E>> boolean hasValue(Class<E> enumClass, Object value) {
    	if (value instanceof Enum<?>) 
            return isInstance(enumClass, (Enum<?>) value);
        else if (value instanceof String)
            return hasStringValue(enumClass, (String) value) || hasOrdinalStringValue(enumClass, (String) value);
        else if (value instanceof Integer)
            return hasOrdinalValue(enumClass, (Integer) value);
        return false;
    }

    private static <E extends Enum<E>> boolean hasStringValue(Class<E> enumClass, String value) {
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    private static <E extends Enum<E>> boolean hasOrdinalValue(Class<E> enumClass, int ordinal) {
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.ordinal() == ordinal) {
                return true;
            }
        }
        return false;
    }

    private static <E extends Enum<E>> boolean hasOrdinalStringValue(Class<E> enumClass, String value) {
        try {
            int ordinal = Integer.parseInt(value);
            return hasOrdinalValue(enumClass, ordinal);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static <E extends Enum<E>> boolean isInstance(Class<E> enumClass, Enum<?> instancia) {
        return enumClass.isInstance(instancia);
    }

}
