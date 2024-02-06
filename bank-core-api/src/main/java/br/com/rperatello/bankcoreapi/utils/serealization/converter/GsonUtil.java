package br.com.rperatello.bankcoreapi.utils.serealization.converter;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.rperatello.bankcoreapi.utils.serealization.adapter.LocalDateTimeAdapter;
import br.com.rperatello.bankcoreapi.utils.serealization.adapter.OptionalTypeAdapterFactory;

public class GsonUtil {
	
	private static Gson gson = new GsonBuilder()
	        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
	        .registerTypeAdapterFactory(new OptionalTypeAdapterFactory())
	        .setLenient()
	        .serializeNulls()
	        .create();

	public static Object Serialize(Object obj) {
        return gson.toJson(obj);
	}	
	
	public static <D> Object Desserialize(String obj, Class<D> modelClass) {
        return gson.fromJson(obj, modelClass);
	}	
	

}



