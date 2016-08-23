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

import com.google.common.collect.Lists;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.anhonesteffort.ads.ad.AdsResource;
import org.anhonesteffort.ads.twitter.ManagedTweetStream;
import org.anhonesteffort.ads.zodiac.ZodiacTweetListener;
import org.anhonesteffort.ads.zodiac.ZodiacTweetListenerFactory;
import org.anhonesteffort.ads.zodiac.ZodiacTweetsResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.List;

public class AdsApiServer extends Application<AdsApiConfig> {

  @Override
  public String getName() {
    return "ads-api";
  }

  private void initCors(Environment env) {
    FilterRegistration.Dynamic corsFilter = env.servlets().addFilter("CORS", CrossOriginFilter.class);
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,OPTIONS");
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
    corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
  }

  @Override
  @SuppressWarnings("unchecked")
  public void run(AdsApiConfig config, Environment environment) {
    List<ZodiacTweetListener> tweetListeners = new ZodiacTweetListenerFactory(config.getZodiac()).create();
    ManagedTweetStream        tweetStream    = new ManagedTweetStream(config.getTwitter(), tweetListeners);

    initCors(environment);
    environment.lifecycle().manage(tweetStream);
    environment.healthChecks().register("dumb", new DumbCheck());
    environment.jersey().register(new PingResource());
    environment.jersey().register(new ZodiacTweetsResource(tweetListeners));
    environment.jersey().register(new AdsResource());

    new ShutdownProcedure(
        Lists.newArrayList(tweetStream.getShutdownFuture())
    ).init();
  }

  public static void main(String[] args) throws Exception {
    new AdsApiServer().run(args);
  }

}
