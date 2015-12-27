package net.dragora.bjsstest.data;

import java.util.List;

/**
 * Created by nietzsche on 27/12/15.
 *
 * Provide the list of items in the shop.
 * Implementation: @ItemsStoreFromRes that retrieve the items from application resources
 * Future: can be implemented a database filled from remote resource
 */
public interface ItemsStore {

    List<Item> getAll();

    List<Item> get(String filter);
}
