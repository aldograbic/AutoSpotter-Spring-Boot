package com.example.AutoSpotter.classes.vehicle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.AutoSpotter.classes.location.LocationRepository;

@Repository
public class JdbcVehicleRepository implements VehicleRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final LocationRepository locationRepository;

    public JdbcVehicleRepository(JdbcTemplate jdbcTemplate, LocationRepository locationRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.locationRepository = locationRepository;
    }

    @Override
    public int saveVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (manufacturer, model, body_type, color, registered, mileage, state, year, number_of_wheels, " +
                    "maximum_allowable_weight, engine_type, engine_displacement, engine_power, fuel_consumption, transmission, drive_train, battery_capacity, " +
                    "charging_time, vehicle_range, city_id, vehicle_type_id, vehicle_safety_features_id, vehicle_extras_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                vehicle.getManufacturer(),
                vehicle.getModel(),
                vehicle.getBodyType(),
                vehicle.getColor(),
                vehicle.getRegistered(),
                vehicle.getMileage(),
                vehicle.getState(),
                vehicle.getYear(),
                vehicle.getNumberOfWheels(),
                vehicle.getMaximumAllowableWeight(),
                vehicle.getEngineType(),
                vehicle.getEngineDisplacement(),
                vehicle.getEnginePower(),
                vehicle.getFuelConsumption(),
                vehicle.getTransmission(),
                vehicle.getDriveTrain(),
                vehicle.getBatteryCapacity(),
                vehicle.getChargingTime(),
                vehicle.getVehicleRange(),
                vehicle.getCityId(),
                vehicle.getVehicleTypeId(),
                vehicle.getVehicleSafetyFeaturesId(),
                vehicle.getVehicleExtrasId()
        );
        String idSql = "SELECT last_insert_id()";
        return jdbcTemplate.queryForObject(idSql, Integer.class);
    }

    @Override
    public int getVehicleTypeId(String vehicleType) {
        String sql = "SELECT id FROM vehicle_type WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, vehicleType);
    }

    public int getCityIdByName(String cityName) {
        String sql = "SELECT id FROM cities WHERE city_name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cityName);
    }

    @Override
    public List<String> getAllVehicleTypes() {
        String sql = "SELECT name FROM vehicle_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public Vehicle getVehicleById(int id) {
        String sql = "SELECT id, manufacturer, model, body_type, color, registered, mileage, state, year, number_of_wheels, maximum_allowable_weight, " +
                    "engine_type, engine_displacement, engine_power, fuel_consumption, transmission, drive_train, battery_capacity, charging_time, vehicle_range, " +
                    "city_id, vehicle_type_id, vehicle_safety_features_id, vehicle_extras_id FROM vehicle WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new VehicleRowMapper(locationRepository), id);
    }

    @Override
    public List<String> getManufacturersByVehicleType(int vehicleTypeId) {
        String sql = "SELECT manufacturer_name FROM manufacturers WHERE vehicle_type_id = ? ";
        return jdbcTemplate.queryForList(sql, String.class, vehicleTypeId);
    }

    @Override
    public List<String> getAllBodyTypes() {
        String sql = "SELECT body_type_name FROM body_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllEngineTypes() {
        String sql = "SELECT engine_type_name FROM engine_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllTransmissionTypes() {
        String sql = "SELECT transmission_name FROM transmission";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllDriveTrainTypes() {
        String sql = "SELECT drive_train_name FROM drive_train";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
public int saveVehicleSafetyFeatures(List<String> safetyFeatures) {
    String sql = "INSERT INTO vehicle_safety_features (abs, esp, central_locking, " +
                 "traction_control, front_side_airbag, rear_side_airbag) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";
    Object[] params = new Object[]{
        safetyFeatures.contains("abs"),
        safetyFeatures.contains("esp"),
        safetyFeatures.contains("central_locking"),
        safetyFeatures.contains("traction_control"),
        safetyFeatures.contains("front_side_airbag"),
        safetyFeatures.contains("rear_side_airbag")
    };
    jdbcTemplate.update(sql, params);
    return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
}

    @Override
    public int saveVehicleExtras(List<String> extras) {
        String sql = "INSERT INTO vehicle_extras (adaptive_cruise_control, " +
                    "air_suspension, alarm_system, ambient_lighting, android_auto, " +
                    "apple_carplay, arm_rest, automatic_air_conditioning, automatic_2_zone_climatisations, " +
                    "automatic_3_zones_climatisation, automatic_4_zones_climatisation, bi_xenon_headlights, " +
                    "bluetooth, blind_spot_assist, cd_player, cruise_control, dab_radio, " +
                    "distance_warning_system, electric_seat_adjustment, electric_side_mirror, electric_windows, " +
                    "emergency_call_system, emergency_tyre_repair_kit, fog_lamp, hands_free_kit, " +
                    "head_up_display, headlight_washer_system, heated_rear_seats, heated_seats, " +
                    "heated_steering_wheel, hill_start_assist, induction_charging_for_smartphones, isofix, " +
                    "keyless_central_locking, lane_change_assist, laser_headlights, leather_steering_wheel, " +
                    "led_headlights, lumbar_support, manual_climatisation, massage_seats, " +
                    "multifunction_steering_wheel, navigation_system, on_board_computer, paddle_shifters, " +
                    "panoramic_roof, parking_sensors, power_assisted_steering, rain_sensor, " +
                    "roof_rack, spare_tyre, sport_seats, start_stop_system, " +
                    "sunroof, traffic_sign_recognition, tyre_pressure_monitoring, usb_port, voice_control) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] params = new Object[]{
            extras.contains("adaptive_cruise_control"),
            extras.contains("air_suspension"),
            extras.contains("alarm_system"),
            extras.contains("ambient_lighting"),
            extras.contains("android_auto"),
            extras.contains("apple_carplay"),
            extras.contains("arm_rest"),
            extras.contains("automatic_air_conditioning"),
            extras.contains("automatic_2_zone_climatisations"),
            extras.contains("automatic_3_zones_climatisation"),
            extras.contains("automatic_4_zones_climatisation"),
            extras.contains("bi_xenon_headlights"),
            extras.contains("bluetooth"),
            extras.contains("blind_spot_assist"),
            extras.contains("cd_player"),
            extras.contains("cruise_control"),
            extras.contains("dab_radio"),
            extras.contains("distance_warning_system"),
            extras.contains("electric_seat_adjustment"),
            extras.contains("electric_side_mirror"),
            extras.contains("electric_windows"),
            extras.contains("emergency_call_system"),
            extras.contains("emergency_tyre_repair_kit"),
            extras.contains("fog_lamp"),
            extras.contains("hands_free_kit"),
            extras.contains("head_up_display"),
            extras.contains("headlight_washer_system"),
            extras.contains("heated_rear_seats"),
            extras.contains("heated_seats"),
            extras.contains("heated_steering_wheel"),
            extras.contains("hill_start_assist"),
            extras.contains("induction_charging_for_smartphones"),
            extras.contains("isofix"),
            extras.contains("keyless_central_locking"),
            extras.contains("lane_change_assist"),
            extras.contains("laser_headlights"),
            extras.contains("leather_steering_wheel"),
            extras.contains("led_headlights"),
            extras.contains("lumbar_support"),
            extras.contains("manual_climatisation"),
            extras.contains("massage_seats"),
            extras.contains("multifunction_steering_wheel"),
            extras.contains("navigation_system"),
            extras.contains("on_board_computer"),
            extras.contains("paddle_shifters"),
            extras.contains("panoramic_roof"),
            extras.contains("parking_sensors"),
            extras.contains("power_assisted_steering"),
            extras.contains("rain_sensor"),
            extras.contains("roof_rack"),
            extras.contains("spare_tyre"),
            extras.contains("sport_seats"),
            extras.contains("start_stop_system"),
            extras.contains("sunroof"),
            extras.contains("traffic_sign_recognition"),
            extras.contains("tyre_pressure_monitoring"),
            extras.contains("usb_port"),
            extras.contains("voice_control")
        };
        jdbcTemplate.update(sql, params);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    @Override
    public List<String> getAllCities() {
        String sql = "SELECT city_name FROM cities";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllCounties() {
        String sql = "SELECT county_name FROM counties";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public Map<String, List<String>> getCitiesByCounty() {
        String sql = "SELECT c.county_name, ct.city_name, ct.county_id " +
                    "FROM cities ct " +
                    "INNER JOIN counties c ON ct.county_id = c.id " +
                    "ORDER BY c.county_name";

        return jdbcTemplate.query(sql, (rs) -> {
            Map<String, List<String>> citiesByCounty = new LinkedHashMap<>();

            while (rs.next()) {
                String countyName = rs.getString("county_name");
                String cityName = rs.getString("city_name");

                if (!citiesByCounty.containsKey(countyName)) {
                    citiesByCounty.put(countyName, new ArrayList<>());
                }

                citiesByCounty.get(countyName).add(cityName);
            }

            return citiesByCounty;
        });
    }


    @Override
    public List<String> getAllStates() {
        String sql = "SELECT name FROM states";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}