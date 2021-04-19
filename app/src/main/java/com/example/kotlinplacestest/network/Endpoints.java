package com.example.kotlinplacestest.network;

import com.example.kotlinplacestest.BuildConfig;

public class Endpoints {
    public static final String PLACES_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=Popeyes&rankby=distance&key=" + BuildConfig.PLACES_API_KEY + "&location=39.290386,-76.612190";
    public static final String PLACES_IMAGE_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key="
            + BuildConfig.PLACES_API_KEY;
    public static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?" + "&key=" + BuildConfig.PLACES_API_KEY;
    //make sure to add location to above base ^
    //example:
    //&location=39.290386,-76.612190
}
