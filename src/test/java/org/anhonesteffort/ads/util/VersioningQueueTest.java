/*
 * Copyright (C) 2016 An Honest Effort LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.anhonesteffort.ads.util;

import org.junit.Test;

public class VersioningQueueTest {

  @Test
  public void test() {
    final int                     CAPACITY = 10;
    final VersioningQueue<String> QUEUE    = new VersioningQueue<>(CAPACITY);

    assert QUEUE.getUpdates(0l).getVersion()        == 0l;
    assert QUEUE.getUpdates(0l).getEntries().size() == 0;

    QUEUE.add("lol");
    QUEUE.add("wut");

    VersionedEntries<String> ENTRIES = QUEUE.getUpdates(0l);
    assert ENTRIES.getVersion() == 2l;
    assert ENTRIES.getEntries().size() == 2;
    assert ENTRIES.getEntries().get(0).getVersion() == 2l;
    assert ENTRIES.getEntries().get(0).getValue().equals("wut");
    assert ENTRIES.getEntries().get(1).getVersion() == 1l;
    assert ENTRIES.getEntries().get(1).getValue().equals("lol");

    ENTRIES = QUEUE.getUpdates(1l);
    assert ENTRIES.getVersion() == 2l;
    assert ENTRIES.getEntries().size() == 1;
    assert ENTRIES.getEntries().get(0).getVersion() == 2l;
    assert ENTRIES.getEntries().get(0).getValue().equals("wut");

    QUEUE.add("qqq");
    QUEUE.add("qqq");
    QUEUE.add("qqq");
    QUEUE.add("qqq");
    QUEUE.add("qqq");
    QUEUE.add("qqq");
    QUEUE.add("qqq");
    QUEUE.add("qqq");
    QUEUE.add("lolwut");

    ENTRIES = QUEUE.getUpdates(0l);
    assert ENTRIES.getVersion() == 11l;
    assert ENTRIES.getEntries().size() == 10;
    assert ENTRIES.getEntries().get(0).getVersion() == 11l;
    assert ENTRIES.getEntries().get(0).getValue().equals("lolwut");
    assert ENTRIES.getEntries().get(1).getVersion() == 10l;
    assert ENTRIES.getEntries().get(1).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(2).getVersion() == 9l;
    assert ENTRIES.getEntries().get(2).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(3).getVersion() == 8l;
    assert ENTRIES.getEntries().get(3).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(4).getVersion() == 7l;
    assert ENTRIES.getEntries().get(4).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(5).getVersion() == 6l;
    assert ENTRIES.getEntries().get(5).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(6).getVersion() == 5l;
    assert ENTRIES.getEntries().get(6).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(7).getVersion() == 4l;
    assert ENTRIES.getEntries().get(7).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(8).getVersion() == 3l;
    assert ENTRIES.getEntries().get(8).getValue().equals("qqq");
    assert ENTRIES.getEntries().get(9).getVersion() == 2l;
    assert ENTRIES.getEntries().get(9).getValue().equals("wut");
  }

}
