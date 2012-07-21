package net.thornydev.mybatis.test.koan11;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class Koan11 {

  static SqlSessionFactory sessionFactory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    String resource = "net/thornydev/mybatis/test/koan11/koan11-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Test
  public void learnToQueryHasManyRelationshipUsingCollection() {
    SqlSession session = null;
    try {
      session = sessionFactory.openSession();
      LanguageMapper mapper = session.getMapper(LanguageMapper.class);

      LanguageK11 lang = mapper.getLanguageById(1);
      assertNotNull(lang);
      assertEquals(1, lang.getId());
      assertEquals("English", lang.getName().trim());
      assertNotNull(lang.getFilms());

      List<FilmK11> lf = lang.getFilms();
      assertEquals(1000, lf.size());

      FilmK11 f = lf.get(0);
      assertEquals(1000, f.getId());
      assertEquals("ZORRO ARK", f.getTitle());
      assertTrue( Pattern.
                  compile("Trailers.+Commentaries.+Behind the Scenes", Pattern.CASE_INSENSITIVE).
                  matcher(f.getSpecialFeatures()).
                  find() );
      // we didn't populate the rating field in the query so should be null
      assertNull(f.getRating());

    } finally {
      if (session != null) session.close();
    }
  }

  @Test
  public void learnToQueryHasManyRelationshipThatHasNoEntitiesInThatRelationship() {
    SqlSession session = null;
    try {
      session = sessionFactory.openSession();
      LanguageMapper mapper = session.getMapper(LanguageMapper.class);

      // NOTE: if this test fails but "learnToQueryHasManyRelationshipUsingCollection"
      // succeeded, you need to modify the SQL in koan11-mapper to return a
      // language even if it is has no films (Hint: an inner join will not work)
      LanguageK11 lang = mapper.getLanguageById(3);
      assertNotNull(lang);
      assertEquals(3, lang.getId());
      assertEquals("Japanese", lang.getName().trim());
      assertNotNull(lang.getFilms());
      // should be no films in Japanese in the db
      assertEquals(0, lang.getFilms().size());

    } finally {
      if (session != null) session.close();
    }
  }

}
