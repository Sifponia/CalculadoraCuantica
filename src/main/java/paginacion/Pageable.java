package paginacion;

import io.vertx.core.json.JsonObject;

import java.util.*;

/**
 * Reference: https://stackoverflow.com/questions/19688235/how-to-implement-pagination-on-a-list
 *
 * @author elvis Gracias :3
 * @version $Revision: $<br/>
 * $Id: $
 * @since 6/21/17 7:28 PM
 */
public class Pageable {

  public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {

    if (pageSize <= 0 || page <= 0) {
      throw new IllegalArgumentException("invalid page size: " + pageSize);
    }

    int fromIndex = (page - 1) * pageSize;
    if (sourceList == null || sourceList.size() < fromIndex) {
      return Collections.emptyList();
    }

    // toIndex exclusive
    return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
  }

  public static <T> void doPaginated(Collection<T> fullList, Integer pageSize, Page<T> pageInterface) {

    final List<T> list = new ArrayList<T>(fullList);
    if (pageSize == null || pageSize <= 0 || pageSize > list.size()) {
      pageSize = list.size();
    }

    final int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
    for (int pageNum = 0; pageNum < numPages; ) {
      final List<T> page = list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size()));
      pageInterface.run(page);
    }
  }

  public interface Page<T> {
    void run(List<T> item);
  }
}


class Test {

  public static void main(String[] args) {


    List<JsonObject> jsonObjects = Arrays.asList(
      new JsonObject().put("id", 1).put("resul", 4),
      new JsonObject().put("id", 2),
      new JsonObject().put("id", 3),
      new JsonObject().put("id", 4),
      new JsonObject().put("id", 5)

      , new JsonObject().put("id", 32));


    final int pageSize = 55;
    final List<List<JsonObject>> results = new ArrayList<>();


    System.out.println(Pageable.getPage(jsonObjects, 3, 2));


  }
}
