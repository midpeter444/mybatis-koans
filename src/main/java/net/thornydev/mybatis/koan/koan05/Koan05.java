package net.thornydev.mybatis.koan.koan05;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.thornydev.mybatis.koan.domain.Country;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// In Koan05, we again create an xml mapper file and this time we create a
// Mapper java interface for it in order to get compile time checking of our
// method invocations.  We call that mapper Koan05Mapper.
// The select statements in the mapper are exactly the same as in Koan03, but
// we have changed their names to make them more natural to the domain.
//
// To complete this koan test you will need to edit:
// 1. all the TODO entries in this koan
// 2. the mapper xml file to have the right SQL queries and MyBatis XML entries
// 3. all the TODO entries in the Koan05Mapper class
public class Koan05 {

  static SqlSessionFactory sessionFactory;
  SqlSession session;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/koan/koan05/koan05-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Before
  public void setUp() throws Exception {
    session = sessionFactory.openSession();
  }

  @After
  public void tearDown() throws Exception {
    if (session != null) session.close();
  }

  @Test
  public void learnToQueryViaMapperClassReturningCountryDomainObject() throws Exception {
    // TODO: create a mapper object
    Koan05Mapper mapper = null;
    // TODO: get the Country with id 1 via the mapper (*not* via session.selectOne())
    Country c = null;

    assertNotNull(c);
    assertEquals(1, c.getId());
    assertEquals("Afghanistan", c.getCountry());

    assertNotNull(c.getLastUpdate());
    String dateStr = c.getLastUpdate().toString();
    assertTrue( Pattern.
                compile("Feb\\s+15.+2006").
                matcher( dateStr ).
                find() );
  }

  @Test
  public void learnToQueryViaMapperClassReturningListOfCountries() throws Exception {
    // TODO: create a mapper object
    Koan05Mapper mapper = null;
    // TODO: get List of all Countries via a mapper method invocation
    List<Country> allCountries = null;

    assertEquals(109, allCountries.size());
    Country c109 = allCountries.get(0);

    assertEquals(109, c109.getId());
    assertEquals("Zambia", c109.getCountry());
  }

  @Test
  public void learnToQueryViaMapperClassReturningHashMapOfCountriesKeyedById() throws Exception {
    // TODO: create a mapper object
    Koan05Mapper mapper = null;
    // TODO: query for a map of all Country objects
    // TODO: fill in the "?" generic param placeholders
    Map<?,?> countriesMap = null;

    assertEquals(109, countriesMap.size());

    // TODO: when you fill in the "?" generic placeholders above
    //       this cast will no longer be necessary - remove it
    Country c33 = (Country)countriesMap.get(33);
    assertEquals(33, c33.getId());
    assertEquals("Finland", c33.getCountry());

    assertNotNull(c33.getLastUpdate());
    String dateStr = c33.getLastUpdate().toString();
    assertTrue( Pattern.
                compile("Feb\\s+15.+2006").
                matcher( dateStr ).
                find() );
  }
}
