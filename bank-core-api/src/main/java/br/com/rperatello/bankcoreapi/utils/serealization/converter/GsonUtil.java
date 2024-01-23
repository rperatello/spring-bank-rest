package br.com.rperatello.bankcoreapi.utils.serealization.converter;

import com.google.gson.Gson;

public class GsonUtil {
	
	private static Gson gson = new Gson();

	public static Object Serialize(Object obj) {
        return gson.toJson(obj);
	}	
	
	public static <D> Object Desserialize(String obj, Class<D> modelClass) {
        return gson.fromJson(obj, modelClass);
	}

}
