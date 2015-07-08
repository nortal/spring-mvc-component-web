package com.nortal.spring.cw.core.web.component.list.header;

import java.io.Serializable;

import com.nortal.spring.cw.core.web.component.list.row.ListRow;

/**
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 09.04.2013
 */
public interface ListFilter extends Serializable {
   // Lauri FIXME: elementValue on vist üleliigne, kui väärtus on kättesaadav ka
   // headerist
   boolean filter(ListHeader header, ListRow listRow, Object elementValue);
}