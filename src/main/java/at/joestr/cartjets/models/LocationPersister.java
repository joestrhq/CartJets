/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joestr.cartjets.models;

import com.google.gson.Gson;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import org.bukkit.Location;

/**
 *
 * @author Joel
 */
public class LocationPersister extends BaseDataType {

  private static final LocationPersister INSTANCE =
    new LocationPersister(
      SqlType.STRING,
      new Class<?>[]{Location.class}
    );
  
  public static LocationPersister getSingleton() {
    return INSTANCE;
  }
  
  public LocationPersister(SqlType sqlType, Class<?>[] classes) {
    super(sqlType, classes);
  }
  
  @Override
  public Object parseDefaultString(FieldType ft, String string) throws SQLException {
    return null;
  }

  @Override
  public Object resultToSqlArg(FieldType ft, DatabaseResults dr, int i) throws SQLException {
    return new Gson().toJson(dr.getObject(i));
  }

  @Override
  public SqlType getSqlType() {
    return SqlType.STRING;
  }

  @Override
  public Object resultStringToJava(FieldType ft, String string, int i) throws SQLException {
    return new Gson().fromJson(string, Location.class);
  }
}
