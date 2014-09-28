package com.zaxxer.hikari.json.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.zaxxer.hikari.json.JsonProperty;

public final class Clazz
{
   private final Class<?> actualClass;
   private final Map<String, Phield> fields;

   public Clazz(Class<?> clazz)
   {
      this.actualClass = clazz;
      this.fields = new HashMap<>();
   }

   void parseFields()
   {
      for (Field field : actualClass.getDeclaredFields()) {
         if (!Modifier.isStatic(field.getModifiers())) {
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
               fields.put(jsonProperty.value(), new Phield(field));
            }
            else {
               fields.put(field.getName(), new Phield(field));
               fields.put(field.getName().toLowerCase(), new Phield(field));
            }
         }
      }
   }

   public Class<?> getActualClass() {
      return actualClass;
   }

   public Phield getPhield(final String name) {
      return fields.get(name);
   }

   @Override
   public String toString()
   {
      return "Clazz [" + actualClass.getCanonicalName() + "]";
   }

   public Object newInstance() throws InstantiationException, IllegalAccessException
   {
      return actualClass.newInstance();
   }
}
