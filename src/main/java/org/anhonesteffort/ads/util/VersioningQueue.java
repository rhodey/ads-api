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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

public class VersioningQueue<T> {

  private final Object txnLock = new Object();
  private final Deque<VersionedEntry<T>> deque;
  private final int capacity;
  private long version = 0;

  public VersioningQueue(int capacity) {
    this.capacity = capacity;
    deque         = new ArrayDeque<>(capacity);
  }

  public VersionedEntry<T> add(T value) {
    synchronized (txnLock) {
      VersionedEntry<T> entry = new VersionedEntry<>(++version, value);
      deque.push(entry);

      if (deque.size() > capacity) {
        deque.removeLast();
      }

      return entry;
    }
  }

  public VersionedEntries<T> getUpdates(long version) {
    synchronized (txnLock) {
      return new VersionedEntries<>(
          this.version, deque.stream().filter(
            entry -> entry.getVersion() > version
          ).collect(Collectors.toList())
      );
    }
  }

}
