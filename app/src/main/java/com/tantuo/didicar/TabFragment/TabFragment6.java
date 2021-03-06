package com.tantuo.didicar.TabFragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.tantuo.didicar.Activity.DiDi_info_Activity;
import com.tantuo.didicar.MainActivity;
import com.tantuo.didicar.R;
import com.tantuo.didicar.base.BaseFragment;
import com.tantuo.didicar.utils.DrivingRouteOverlay;
import com.tantuo.didicar.utils.LogUtil;
import com.tantuo.didicar.utils.PoiOverlay;
import com.tantuo.didicar.utils.WebDetailActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class TabFragment6 extends BaseFragment implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener, View.OnClickListener {


    private NestedScrollView bottomSheetView;
    private ImageView iv_bottom_sheet_item1;
    private ImageView iv_bottom_sheet_item2;
    private ImageView iv_bottom_sheet_item3;
    private ImageView iv_bottom_sheet_item4;
    private ImageView iv_bottom_sheet_item5;
    private ImageView iv_bottom_sheet_item6;
    private ImageView iv_bottom_sheet_item7;
    private ImageView iv_bottom_sheet_item8;
    private ImageView floating_safety_center;
    private android.support.design.widget.FloatingActionButton floating_locate_center;
    private String iv_bottom_sheet_item_url1 = "https://xmall.xiaojukeji.com/imall/index.htm?xmallsource=1002&sidechanne=3002&access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=3012&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.394839795143554&lng=116.8487379582826&model=HWI-AL00&os=9&phone=W471piXc0R0glRFq7nvDow==&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&time=1560572808586&uid=281867467423745&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&vcode=553&trip_country=CN&location_country=CN&TripCountry=CN&trip_cityId=1&trip_cityid=1&location_cityid=1&utc_offset=480&maptype=soso&origin_id=1&terminal_id=1&source=weixin_source&role=1&shared=tru";
    private String iv_bottom_sheet_item_url2 = "https://dpubstatic.udache.com/static/dpubimg/dpub2_project_187481/index_187481.html?TripCountry=CN&access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=780&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&flat=40.39293&flng=116.84192&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.392355381081394&lng=116.8424214994192&location_cityid=1&location_country=CN&maptype=soso&model=HWI-AL00&origin_id=1&os=9&phone=W471piXc0R0glRFq7nvDow&pid=1_xID-B2_hV&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&terminal_id=1&time=1560742235707&trip_cityId=1&trip_cityid=1&trip_country=CN&uid=281867467423745&utc_offset=480&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&";
    private String iv_bottom_sheet_item_url3 = "https://dpubstatic.udache.com/static/dpubimg/76f185ec7e0a18a60935cf2673c1020f/index.html?TripCountry=CN&access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=780&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.3949025796631&lng=116.84876880903553&location_cityid=1&location_country=CN&maptype=soso&model=HWI-AL00&origin_id=1&os=9&phone=W471piXc0R0glRFq7nvDow&pid=1_2hJ3RccxA&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&terminal_id=1&time=1560760307676&trip_cityId=1&trip_cityid=1&trip_country=CN&uid=281867467423745&utc_offset=480&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&vcode=553&from=singlemess";
    private String iv_bottom_sheet_item_url4 = "https://dpubstatic.udache.com/static/dpubimg/76f185ec7e0a18a60935cf2673c1020f/index.html?TripCountry=CN&access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=780&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.3979418911976&lng=116.8437601867656&location_cityid=1&location_country=CN&maptype=soso&model=HWI-AL00&origin_id=1&os=9&phone=W471piXc0R0glRFq7nvDow&pid=1_m8AD23H4n&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&terminal_id=1&time=1560759621077&trip_cityId=1&trip_cityid=1&trip_country=CN&uid=281867467423745&utc_offset=480&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&vcode=553";
    private String iv_bottom_sheet_item_url5 = "https://dpubstatic.udache.com/static/dpubimg/b6d6d1436f5094959a4289a4deace69c/index.html?TripCountry=CN&access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=780&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.3949025796631&lng=116.84876880903553&location_cityid=1&location_country=CN&maptype=soso&model=HWI-AL00&origin_id=1&os=9&phone=W471piXc0R0glRFq7nvDow&pid=1_26PHFy8uc&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&terminal_id=1&time=1560760439334&trip_cityId=1&trip_cityid=1&trip_country=CN&uid=281867467423745&utc_offset=480&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&vcode=553";
    private String iv_bottom_sheet_item_url6 = "https://page.xiaojukeji.com/market/ddPage_0CQ3KMow.html?access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=780&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.39493203251291&lng=116.84898101505226&model=HWI-AL00&os=9&phone=W471piXc0R0glRFq7nvDow==&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&time=1560740676668&uid=281867467423745&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&vcode=553&trip_country=CN&location_country=CN&TripCountry=CN&trip_cityId=1&trip_cityid=1&location_cityid=1&utc_offset=480&maptype=soso&origin_id=1&terminal_id=1&stm=2%257C4ad7bc9d-0a78-464c-8f45-6f3cf3c66fa9%257C4ad7bc9d-0a78-464c-8f45-6f3cf3c66fa9";
    private String iv_bottom_sheet_item_url7 = "https://page.xiaojukeji.com/m/collage.html?openid=oDe7ajrFd0t6KdDKIYjjiCdTd2WA&acctoken=de14351d8744e998f37627267f2c5f4b&needuserinfo=0#/";
    private String floating_safety_center_url = "https://dpubstatic.udache.com/static/dpubimg/dpub2_project_187481/index_187481.html?TripCountry=CN&access_key_id=2&appid=10000&appversion=5.2.52&area=%E5%8C%97%E4%BA%AC%E5%B8%82&channel=780&city_id=1&cityid=1&datatype=1&deviceid=6cd1d3832da36056681ad4ed7ade2155&dviceid=6cd1d3832da36056681ad4ed7ade2155&flat=40.39293&flng=116.84192&imei=868227037142403854C78AD10B66380C8F28CC6327C3788&lang=zh-CN&lat=40.392355381081394&lng=116.8424214994192&location_cityid=1&location_country=CN&maptype=soso&model=HWI-AL00&origin_id=1&os=9&phone=W471piXc0R0glRFq7nvDow&pid=1_xID-B2_hV&platform=1&susig=e4f80d8df39b46ae679cb58d721db&suuid=A1702CD0DD1175EDF286DE35369DF4CA_780&terminal_id=1&time=1560742235707&trip_cityId=1&trip_cityid=1&trip_country=CN&uid=281867467423745&utc_offset=480&uuid=A0AF094F9D975FBBAE7AD129E96CF26F&";


    private static final String TAG = TabFragment6.class.getSimpleName();
    private final String title;
    private final String contents;
    public MapView mMapView;
    private LocationMode mCurrentMode;
    public static BaiduMap mBaiduMap;
    private BottomSheetBehavior<View> sheetBehavior;
    private View rootview;
    BitmapDescriptor mCurrentMarker;

    boolean isFirstLoc = true; // ??????????????????
    private MyLocationData locData;
    private float direction;
    private String currenyCity;

    //Poi???????????????????????????????????????????????????
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private AutoCompleteTextView start_text_view = null;
    private AutoCompleteTextView destin_text_view = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;
    private int searchType = 0;  // ????????????????????????????????????
    private String Flag_Tocken;
    private String start_str;
    private String destin_str;
    private Intent intent;
    private String iv_bottom_sheet_item1_url;
    private MapStatus.Builder builder;

    public TabFragment6(String title, String contents) {
        super();
        this.title = title;
        this.contents = contents;

    }

    @Override
    public View initView() {

        rootview = View.inflate(getActivity(), R.layout.callcar_tab_fragment_0, null);
        LogUtil.i("?????????: " + getClass().getSimpleName() + "  ??????????????????" + gettitle() + ",  ??????:initView()  ");

        mMapView = rootview.findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();


        findview();

        initSearchLocation();

        initBottomSheet();

        return rootview;


    }


    private void findview() {
        bottomSheetView = (NestedScrollView) rootview.findViewById(R.id.bottomSheetView);

        start_text_view = (AutoCompleteTextView) rootview.findViewById(R.id.tv_start_location);
        destin_text_view = (AutoCompleteTextView) rootview.findViewById(R.id.tv_destin_location);
        floating_safety_center = rootview.findViewById(R.id.floating_safety_center);
        floating_locate_center = rootview.findViewById(R.id.floating_locate_center);


        if (MainActivity.currentBuilding != null) {
            start_text_view.setHint("?????????????????? " + MainActivity.currentStreet + MainActivity.currentBuilding);
        } else {
            start_text_view.setHint("?????????????????? " + MainActivity.currentStreet);
        }


        iv_bottom_sheet_item1 = (ImageView) rootview.findViewById(R.id.iv_bottom_sheet_item1);
        iv_bottom_sheet_item2 = (ImageView) rootview.findViewById(R.id.iv_bottom_sheet_item2);
        iv_bottom_sheet_item3 = (ImageView) rootview.findViewById(R.id.iv_bottom_sheet_item3);
        iv_bottom_sheet_item4 = (ImageView) rootview.findViewById(R.id.iv_bottom_sheet_item4);
        iv_bottom_sheet_item5 = (ImageView) rootview.findViewById(R.id.iv_bottom_sheet_item5);
        iv_bottom_sheet_item6 = (ImageView) rootview.findViewById(R.id.iv_bottom_sheet_item6);
        iv_bottom_sheet_item7 = (ImageView) rootview.findViewById(R.id.iv_bottom_sheet_item7);

        iv_bottom_sheet_item1.setOnClickListener(this);
        iv_bottom_sheet_item2.setOnClickListener(this);
        iv_bottom_sheet_item3.setOnClickListener(this);
        iv_bottom_sheet_item4.setOnClickListener(this);
        iv_bottom_sheet_item5.setOnClickListener(this);
        iv_bottom_sheet_item6.setOnClickListener(this);
        iv_bottom_sheet_item7.setOnClickListener(this);
        floating_safety_center.setOnClickListener(this);
        floating_locate_center.setOnClickListener(this);


    }

    private void initSearchLocation() {
        // ????????????????????????????????????????????????
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // ????????????????????????????????????????????????????????????
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);


        sugAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line);
        start_text_view.setAdapter(sugAdapter);
        start_text_view.setThreshold(1);

        destin_text_view.setAdapter(sugAdapter);
        destin_text_view.setThreshold(1);


        /* ???????????????????????????????????????????????????????????? */
        start_text_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Flag_Tocken = "start_text_view";
                if (cs.length() <= 0) {
                    return;
                }


                /* ??????????????????????????????????????????????????????onSuggestionResult()????????? */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(cs.toString())
                        .city(MainActivity.CurrentCity));

                MainActivity.HideKeyboard(rootview);

                start_str = start_text_view.getText().toString();

                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        //???????????????
                        .city(MainActivity.CurrentCity)
                        //???????????????????????????????????????
                        .pageCapacity(20)
                        //autocompleteTextView??????????????????
                        .keyword(start_str)
                        //??????????????????????????????????????????????????????
                        .pageNum(0)
                        //scope?????????1???????????????????????????2????????????POI????????????
                        .scope(1));
            }
        });

        /* ?????????????????????????????????????????????????????? */
        destin_text_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }


            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Flag_Tocken = "destin_text_view";
                LogUtil.i("?????????: " + getClass().getSimpleName() + " ??????:onTextChanged() destin_text_view ");

                if (cs.length() <= 0) {
                    return;
                }



                /* ??????????????????????????????????????????????????????onSuggestionResult()????????? */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(cs.toString())
                        .city(MainActivity.CurrentCity));
                MainActivity.imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                destin_str = destin_text_view.getText().toString();


                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        //???????????????
                        .city(MainActivity.CurrentCity)
                        //???????????????????????????????????????
                        .pageCapacity(20)
                        //autocompleteTextView??????????????????
                        .keyword(destin_str)
                        //??????????????????????????????????????????????????????
                        .pageNum(0)
                        //scope?????????1???????????????????????????2????????????POI????????????
                        .scope(1));

            }
        });
    }


    /**
     * ??????POI?????????????????????searchInCity???searchNearby???searchInBound?????????????????????
     *
     * @param result Poi???????????????????????????????????????????????????????????????
     */
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            LogUtil.i("?????????:" + getClass().getSimpleName() + "  ??????:onGetPoiResult() ??????????????? ");

            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {


            mBaiduMap.clear();

            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();

            builder = new MapStatus.Builder();
            PoiInfo poi1 = result.getAllPoi().get(0);

            switch (Flag_Tocken) {
                case "initData":

                    builder.target(MainActivity.startll).zoom(15.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()), 2000);

                    break;
                case "start_text_view":
                    //overlay.zoomToSpan();

                    builder.target(poi1.getLocation()).zoom(16.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()), 2000);

                    break;
                case "destin_text_view":

                    RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
                    mSearch = RoutePlanSearch.newInstance();

                    mSearch.setOnGetRoutePlanResultListener(listener);
                    PlanNode stNode = PlanNode.withCityNameAndPlaceName("??????", start_str);
                    PlanNode enNode = PlanNode.withCityNameAndPlaceName("??????", destin_str);

                    //  ????????????DrivingRoutePlanOption()?????????????????????????????????????????????
                    //  ECAR_TIME_FIRST ???????????????????????????????????????
                    //  ECAR_FEE_FIRST  ???????????????????????????????????????
                    //  ECAR_DIS_FIRST  ???????????????????????????????????????

                    mSearch.drivingSearch((new DrivingRoutePlanOption())
                            .from(stNode)
                            .to(enNode));

                    builder.target(poi1.getLocation()).zoom(15.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()), 2000);
                    overlay.zoomToSpan();

                    MainActivity.HideKeyboard(rootview);

                    break;
                default:
                    break;

            }


            return;
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            String strInfo = "???";

            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }

            strInfo += "????????????";
            LogUtil.i("?????????:TabFragment0, ??????:onGetPoiResult()" + strInfo);
        }
    }

    private void initBottomSheet() {

        View bottomSheetView = rootview.findViewById(R.id.bottomSheetView);

        //??????behavior
        sheetBehavior = BottomSheetBehavior.from(bottomSheetView);

        //???????????????????????????, ?????????????????????BottomSheetBehavior.STATE_COLLAPSED
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //??????sheet???????????????????????????????????????
        sheetBehavior.setHideable(false);

        //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????true?????????
        sheetBehavior.setSkipCollapsed(false);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //?????????bottomSheet ???????????????
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //????????????????????????????????????slideOffset?????????????????????
            }
        });
    }


    @Override
    public void initData() {

        Flag_Tocken = "initData";
        LogUtil.i("?????????: " + getClass().getSimpleName() + "  ??????????????????" + gettitle() + ",  ??????:initData()  ");
        super.initData();

        mBaiduMap.clear();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.setBuildingsEnabled(true);
        mBaiduMap.setCompassEnable(true);


        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_pin1);
        mCurrentMode = LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        mBaiduMap.setMyLocationData(MainActivity.startlocData);


        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                //???????????????????????????????????????????????????????????????

                .keyword("???")
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(MainActivity.startll)
                .radius(2000)
                //??????????????????????????????????????????????????????
                .pageNum(0)
                //???????????????????????????????????????
                .pageCapacity(10)
                //scope?????????1???????????????????????????2????????????POI????????????
                .scope(1);

        mPoiSearch.searchNearby(nearbySearchOption);


    }

    @Override
    public String gettitle() {
        return title;
    }


    /**
     * ??????POI???????????????????????????searchPoiDetail?????????????????????
     * V5.2.0???????????????????????????????????????{@link #onGetPoiDetailResult(PoiDetailSearchResult)}??????
     *
     * @param result POI??????????????????
     */
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            LogUtil.i("?????????:TabFragment0, ??????:onGetPoiDetailResult() ???????????????????????? ");
        } else {
            LogUtil.i("?????????:TabFragment0, ??????:onGetPoiDetailResult()  " + result.getName() + ": " + result.getAddress());
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        if (poiDetailSearchResult.error != SearchResult.ERRORNO.NO_ERROR) {
            LogUtil.i("?????????:TabFragment0, ??????:onGetPoiDetailResult() ???????????????????????? ");
        } else {
            List<PoiDetailInfo> poiDetailInfoList = poiDetailSearchResult.getPoiDetailInfoList();
            if (null == poiDetailInfoList || poiDetailInfoList.isEmpty()) {
                LogUtil.i("?????????:TabFragment0, ??????:onGetPoiDetailResult() ??????????????????????????? ");
                return;
            }

            for (int i = 0; i < poiDetailInfoList.size(); i++) {
                PoiDetailInfo poiDetailInfo = poiDetailInfoList.get(i);
                if (null != poiDetailInfo) {

                }
            }
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /**
     * ???????????????????????????????????????requestSuggestion?????????????????????
     *
     * @param res Sug????????????
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }

        List<String> suggest = new ArrayList<>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }

        sugAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line,
                suggest);
        start_text_view.setAdapter(sugAdapter);
        destin_text_view.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        start_text_view.setText("");
        destin_text_view.setText("");
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {

        // ??????????????????
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.floating_safety_center:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), floating_safety_center_url);
                break;

            case R.id.floating_locate_center:

                builder.target(MainActivity.startll).zoom(15.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()), 2000);
                break;

            case R.id.iv_bottom_sheet_item1:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), iv_bottom_sheet_item_url1);
                break;

            case R.id.iv_bottom_sheet_item2:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), iv_bottom_sheet_item_url2);
                break;

            case R.id.iv_bottom_sheet_item3:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), iv_bottom_sheet_item_url3);
                break;

            case R.id.iv_bottom_sheet_item4:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), iv_bottom_sheet_item_url4);
                break;

            case R.id.iv_bottom_sheet_item5:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), iv_bottom_sheet_item_url5);
                break;

            case R.id.iv_bottom_sheet_item6:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), iv_bottom_sheet_item_url6);
                break;

            case R.id.iv_bottom_sheet_item7:

                WebDetailActivityUtils.start_DiDi_info_Activity(this.getActivity(), iv_bottom_sheet_item_url7);
                break;


        }
    }

    private void start_DiDi_info_Activity(String url) {
        intent = new Intent(context, DiDi_info_Activity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


    private class MyPoiOverlay extends PoiOverlay {
        MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
            // }
            return true;
        }

    }

    /**
     * ????????????????????????????????????
     *
     * @param center ???????????????????????????
     * @param radius ??????????????????????????????
     */
    public void showNearbyArea(LatLng center, int radius) {
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
        mBaiduMap.addOverlay(ooMarker);

        OverlayOptions ooCircle = new CircleOptions().fillColor(0xCCCCCC00)
                .center(center)
                .stroke(new Stroke(1, 0xFFFF00FF))
                .radius(100);

        mBaiduMap.addOverlay(ooCircle);
    }


    /**
     * ?????????????????????????????????????????????
     */
    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }


        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                LogUtil.i("?????????:TabFragment0, ??????:onGetDrivingRouteResult() ???????????????????????? ");

            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //?????????????????????????????????????????????????????????????????????????????????
                result.getSuggestAddrInfo();
                LogUtil.i("?????????:TabFragment0, ??????:onGetDrivingRouteResult() ???????????????????????????????????? ");

                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {

                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                if (result.getRouteLines().size() > 0) {
                    //????????????????????????,(????????????????????????????????????
                    //???DrivingRouteOverlay??????????????????
                    overlay.setData(result.getRouteLines().get(0));
                    //??????????????????DrivingRouteOverlay
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    LogUtil.i("?????????:TabFragment0, ??????:onGetDrivingRouteResult() ?????????<0 ");
                    return;
                }

            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };
}
