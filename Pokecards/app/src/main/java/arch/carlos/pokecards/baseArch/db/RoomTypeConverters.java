/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package arch.carlos.pokecards.baseArch.db;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import arch.carlos.pokecards.baseArch.cache.DomainCache;
import arch.carlos.pokecards.pokeApp.vo.components.Ability;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomTypeConverters {

    @TypeConverter
    public static Ability getAbility(String json) {
        return getData(json);
    }

    @TypeConverter
    public static String persisitAbility(Ability data) {
        return getDataField(data);
    }

    @TypeConverter
    public static ArrayList<String> getStringList(String json) {
        return getData(json);
    }

    @TypeConverter
    public static String persistStringList(ArrayList<String> data) {
        return getDataField(data);
    }

    static <T> String getDataField(T data){
        Type type = new TypeToken<T>() {}.getType();
        Gson gson = new Gson();
        return gson.toJson(data,type);
    }
    static <T> T getData(String json){
        Type type = new TypeToken<T>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json,type);
    }

}
