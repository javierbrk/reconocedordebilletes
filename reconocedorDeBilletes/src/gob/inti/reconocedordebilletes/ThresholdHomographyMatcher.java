package gob.inti.reconocedordebilletes;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DMatch;

public class ThresholdHomographyMatcher extends HomographyMatcher {
	@Override
	protected MatOfDMatch SeleccionDeGoodMatches (Escena esc,Billete b){
		MatOfDMatch good_matches = new MatOfDMatch();
		
		MatOfDMatch matches = new MatOfDMatch();
		double max_dist = 0, min_dist = 99;
		
		matcher.match(b.bTemplate.Descriptores, esc.Descriptores, matches);
	    
	    		
	    List<DMatch> matches_list = matches.toList();		
	    for( int i = 0; i < b.bTemplate.Descriptores.rows(); i++ )
	    { 
	        double dist = matches_list.get(i).distance;
	        if( dist < min_dist )
	            min_dist = dist;
	        if( dist > max_dist )
	            max_dist = dist;
	    }

	    for( int i = 0; i < b.bTemplate.Descriptores.rows(); i++ )
	    { 
	        if( matches_list.get(i).distance < 3*min_dist ){
	            MatOfDMatch temp = new MatOfDMatch();
	            temp.fromArray(matches.toArray()[i]);
	             good_matches.push_back(temp);
	        }
	        
	    }
	    return good_matches;
	}
}
