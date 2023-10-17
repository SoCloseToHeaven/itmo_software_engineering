package com.soclosetoheaven.common.collectionmanager;

import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.util.Observable;

import java.util.List;

public interface DragonCollectionManager extends CollectionManager<Dragon>, Observable<List<Dragon>> {

    boolean removeByID(int id);

    boolean update(Dragon dragon, int id);

    Dragon getByID(int id);

    boolean removeAll(List<Dragon> dragons);
}
