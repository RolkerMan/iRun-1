package com.example.irun;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.LocationSource;
import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MyMapFragment extends Fragment implements LocationSource,AMapLocationListener, 
                       OnCheckedChangeListener {
 
	private AMap aMap;
	private MapView mapView;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private RadioGroup mGPSModeGroup;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		
		mapView = (MapView) view.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		
		mGPSModeGroup = (RadioGroup) view.findViewById(R.id.gps_radio_group);
		mGPSModeGroup.setOnCheckedChangeListener(this);
		
		init();
		
		return view;
	}

	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}		
	}
	
	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		
		aMap.setMapType(AMap.MAP_TYPE_NORMAL);
		
		//地图缩放级别为4-20级
		//如果想让地图放大到最大，18是一个合适值
		CameraUpdate cu = CameraUpdateFactory.zoomTo(18);
		aMap.animateCamera(cu, null);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.mapNormal:
			aMap.setMapType(AMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapSatellite:
			aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
			break;
		}	
	}
	
	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation arg0) {
		if (mListener != null && arg0 != null) {
			if (arg0 != null
					&& arg0.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(arg0);// 显示系统小蓝点
			} else {
				Log.e("AmapErr","Location ERR:" + arg0.getAMapException().getErrorCode());
			}
		}	
	}
	
	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener arg0) {
		mListener = arg0;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(getActivity());
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用destroy()方法
			// 其中如果间隔时间为-1，则定位只定一次
			// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 60 * 1000, 10, this);
		}	
	}
	
	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;	
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
        mapView.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
        mapView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
        mapView.onResume();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	    mapView.onSaveInstanceState(outState);
	}
}
