package db;

import entities.History;
import org.springframework.stereotype.Component;

/**
 * .
 * User: Ostap
 * Date: 5/25/14
 * Time: 8:33 PM
 */

@Component("historyDao")
public class HistoryDaoImpl extends GenericDaoImpl<History> implements HistoryDao {
}
