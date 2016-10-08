/*******************************************************************************
 * Copyright (C) 2013  Stefan Schroeder
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package jsprit.core.util;

public class EuclideanDistanceCalculator {

    public static double calculateDistance(Coordinate coord1, Coordinate coord2) {
        
        double lng1=coord1.getX();
        double lat1=coord1.getY();
        double lng2=coord2.getX();
        double lat2=coord2.getY();
        double lat = (lat1 - lat2) / 2.0;
		double lng = (lng1 - lng2) / 2.0;
		double cache = Math.sin(Math.PI / 180 * lat) * Math.sin(Math.PI / 180 * lat) + Math.cos(Math.PI / 180 * lat1)
				* Math.cos(Math.PI / 180 * lat2) * Math.sin(Math.PI / 180 * lng) * Math.sin(Math.PI / 180 * lng);

		double R = 6378137;
		double S = 2 * R * Math.asin(Math.sqrt(cache));
//		System.out.println(S/250.0+" "+S);
//        return (int)Math.rint(S/250.0);
//        return S;
		return Math.round(S/250.0);
    }

}
