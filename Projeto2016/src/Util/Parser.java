package Util;

public final class Parser{

    public static <T extends Object> T parse(Object from, T toOrDefault){
        if (from == null || (from instanceof String && ((String)from).isEmpty()))
        {
            return toOrDefault;
        }
        try
        {
            T t =   (T) toOrDefault instanceof Short ? (T)Short.valueOf(from.toString()) :
                    (T) toOrDefault instanceof Float ? (T)Float.valueOf(from.toString()) :
                    (T) toOrDefault instanceof Integer ? (T)Integer.valueOf(from.toString()) :
                    (T) toOrDefault instanceof Long ? (T)Long.valueOf(from.toString()) :
                    (T) toOrDefault instanceof Double ? (T)Double.valueOf(from.toString()) :
                    (T) toOrDefault instanceof Boolean ? (T)Boolean.valueOf(from.toString()) :
                    (T) toOrDefault instanceof String ? (T)String.valueOf(from.toString()) : (T)from;

            return t;
        }
        catch (Exception ex)
        {
            return toOrDefault;
        }
    }
}
