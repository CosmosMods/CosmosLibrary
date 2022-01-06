package com.tcn.cosmoslibrary.registry.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tcn.cosmoslibrary.common.enums.EnumGeneralAllowState;

public class GsonAdapterGeneralAllowState implements JsonSerializer<EnumGeneralAllowState>, JsonDeserializer<EnumGeneralAllowState> {

	public GsonAdapterGeneralAllowState() { }

	@Override
	public EnumGeneralAllowState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		
		int index = object.get("index").getAsInt();
		
		return EnumGeneralAllowState.getStateFromIndex(index);
	}

	@Override
	public JsonElement serialize(EnumGeneralAllowState src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		
		object.addProperty("index", src.getIndex());
		object.addProperty("name", src.getName());
		
		return object;
	}
}