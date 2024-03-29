package com.tcn.cosmoslibrary.registry.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tcn.cosmoslibrary.common.nbt.CosmosNBTHelper.Const;
import com.tcn.cosmoslibrary.registry.gson.object.ObjectDestinationInfo;

import net.minecraft.core.BlockPos;

public class GsonAdapterTeleportPos implements JsonSerializer<ObjectDestinationInfo>, JsonDeserializer<ObjectDestinationInfo> {

	public GsonAdapterTeleportPos() { }

	@Override
	public ObjectDestinationInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		JsonObject block_pos = object.getAsJsonObject(Const.NBT_POS_KEY);
		
		float yaw = object.get(Const.NBT_POS_YAW_KEY).getAsFloat();
		float pitch = object.get(Const.NBT_POS_PITCH_KEY).getAsFloat();
		
		int x = block_pos.get(Const.NBT_POS_X_KEY).getAsInt();
		int y = block_pos.get(Const.NBT_POS_Y_KEY).getAsInt();
		int z = block_pos.get(Const.NBT_POS_Z_KEY).getAsInt();
		
		BlockPos pos = new BlockPos(x, y, z);
		
		return new ObjectDestinationInfo(pos, yaw, pitch);
	}

	@Override
	public JsonElement serialize(ObjectDestinationInfo src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		JsonObject block_pos = new JsonObject();
		
		BlockPos pos = src.getPos();
		
		block_pos.addProperty(Const.NBT_POS_X_KEY, pos.getX());
		block_pos.addProperty(Const.NBT_POS_Y_KEY, pos.getY());
		block_pos.addProperty(Const.NBT_POS_Z_KEY, pos.getZ());
		
		object.addProperty(Const.NBT_POS_YAW_KEY, src.getYaw());
		object.addProperty(Const.NBT_POS_PITCH_KEY, src.getPitch());
		object.add(Const.NBT_POS_KEY, block_pos);
		
		return object;
	}
}