package br.com.rperatello.bankcoreapi.utils.converters;

public class EnumConverter {
	
	@SuppressWarnings("null")
	public static <E extends Enum<E>> E convertToEnum(Class<E> enumClass, String value) {
        try {
        	if(value != null) throw new IllegalArgumentException(String.format("Cannot convert null to enum %s", enumClass.getName()));
        	value = value.trim().toUpperCase();
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            try {
                return Enum.valueOf(enumClass, value.toUpperCase());
            } catch (IllegalArgumentException ex) {
                try {
                    return Enum.valueOf(enumClass, ((Enum<?>) Enum.valueOf(enumClass, value)).name());
                } catch (IllegalArgumentException exc) {
                    throw new IllegalArgumentException(String.format("Cannot convert %s to enum %s", value, enumClass.getName()), exc);
                }
            }
        }
    }

    public static <E extends Enum<E>> E convertToEnum(Class<E> enumClass, int value) {
        try {
            return enumClass.getEnumConstants()[value];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Cannot convert %s to enum %s", value, enumClass.getName()), e);
        }
    }

    @SuppressWarnings("unchecked")
	public static <E extends Enum<E>> E convertToEnum(Class<E> enumClass, Enum<E> value) {
        if (enumClass.isInstance(value)) {
            return (E) value;
        } else {
            throw new IllegalArgumentException(String.format("Different enum type found. Expected: %s", enumClass.getName()));
        }
    }
}
