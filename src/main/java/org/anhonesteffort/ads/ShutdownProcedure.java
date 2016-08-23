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

package org.anhonesteffort.ads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ShutdownProcedure {

  private static final Logger log = LoggerFactory.getLogger(ShutdownProcedure.class);
  private final List<CompletableFuture<Void>> shutdownFutures;

  public ShutdownProcedure(List<CompletableFuture<Void>> shutdownFutures) {
    this.shutdownFutures = shutdownFutures;
  }

  public void init() {
    shutdownFutures.forEach(future -> {
      future.handle((ok, ex) -> {
        if (ex != null) {
          log.error("shutting down with error", ex);
          System.exit(1);
        } else {
          log.error("shutting down with unknown error");
          System.exit(2);
        }

        return null;
      });
    });
  }

}
