package com.example.AutoSpotter.classes.vehicle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
        return jdbcTemplate.queryForObject(sql, new VehicleRowMapper(locationRepository, this), id);
    }

    @Override
    public List<String> getManufacturersByVehicleType(int vehicleTypeId) {
        String sql = "SELECT manufacturer_name FROM manufacturers WHERE vehicle_type_id = ? ";
        return jdbcTemplate.queryForList(sql, String.class, vehicleTypeId);
    }

    @Override
    public List<String> getManufacturersByVehicleTypeName(String vehicleType) {
        String sql = "SELECT DISTINCT m.manufacturer_name " +
                    "FROM manufacturers m " +
                    "INNER JOIN vehicle_type vt ON m.vehicle_type_id = vt.id " +
                    "WHERE vt.name = ?";
        return jdbcTemplate.queryForList(sql, String.class, vehicleType);
    }

    @Override
    public List<String> getModelsByManufacturer(String manufacturer) {
        String sql = "SELECT DISTINCT mo.model_name " +
                    "FROM models mo " +
                    "INNER JOIN manufacturers ma ON mo.manufacturer_id = ma.id " +
                    "WHERE ma.manufacturer_name = ?";
        return jdbcTemplate.queryForList(sql, String.class, manufacturer);
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
    public List<String> getAllStates() {
        String sql = "SELECT name FROM states";
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
    public List<String> getVehicleSafetyFeatures(int safetyFeaturesId) {
        String sql = "SELECT abs, esp, central_locking, traction_control, front_side_airbag, rear_side_airbag " +
                     "FROM vehicle_safety_features WHERE id = ?";
        return jdbcTemplate.query(sql, (ResultSetExtractor<List<String>>) rs -> {
            List<String> safetyFeatures = new ArrayList<>();
            if (rs.next()) {
                safetyFeatures.add(rs.getString("abs"));
                safetyFeatures.add(rs.getString("esp"));
                safetyFeatures.add(rs.getString("central_locking"));
                safetyFeatures.add(rs.getString("traction_control"));
                safetyFeatures.add(rs.getString("front_side_airbag"));
                safetyFeatures.add(rs.getString("rear_side_airbag"));
            }
            return safetyFeatures;
        }, safetyFeaturesId);
    }


    @Override
    public List<String> getVehicleExtras(int extrasId) {
        String sql = "SELECT adaptive_cruise_control, air_suspension, alarm_system, ambient_lighting, android_auto, " +
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
                     "sunroof, traffic_sign_recognition, tyre_pressure_monitoring, usb_port, voice_control " +
                     "FROM vehicle_extras WHERE id = ?";
        return jdbcTemplate.query(sql, (ResultSetExtractor<List<String>>) rs -> {
            List<String> extras = new ArrayList<>();
            if (rs.next()) {
                extras.add(rs.getString("adaptive_cruise_control"));
                extras.add(rs.getString("air_suspension"));
                extras.add(rs.getString("alarm_system"));
                extras.add(rs.getString("ambient_lighting"));
                extras.add(rs.getString("android_auto"));
                extras.add(rs.getString("apple_carplay"));
                extras.add(rs.getString("arm_rest"));
                extras.add(rs.getString("automatic_air_conditioning"));
                extras.add(rs.getString("automatic_2_zone_climatisations"));
                extras.add(rs.getString("automatic_3_zones_climatisation"));
                extras.add(rs.getString("automatic_4_zones_climatisation"));
                extras.add(rs.getString("bi_xenon_headlights"));
                extras.add(rs.getString("bluetooth"));
                extras.add(rs.getString("blind_spot_assist"));
                extras.add(rs.getString("cd_player"));
                extras.add(rs.getString("cruise_control"));
                extras.add(rs.getString("dab_radio"));
                extras.add(rs.getString("distance_warning_system"));
                extras.add(rs.getString("electric_seat_adjustment"));
                extras.add(rs.getString("electric_side_mirror"));
                extras.add(rs.getString("electric_windows"));
                extras.add(rs.getString("emergency_call_system"));
                extras.add(rs.getString("emergency_tyre_repair_kit"));
                extras.add(rs.getString("fog_lamp"));
                extras.add(rs.getString("hands_free_kit"));
                extras.add(rs.getString("head_up_display"));
                extras.add(rs.getString("headlight_washer_system"));
                extras.add(rs.getString("heated_rear_seats"));
                extras.add(rs.getString("heated_seats"));
                extras.add(rs.getString("heated_steering_wheel"));
                extras.add(rs.getString("hill_start_assist"));
                extras.add(rs.getString("induction_charging_for_smartphones"));
                extras.add(rs.getString("isofix"));
                extras.add(rs.getString("keyless_central_locking"));
                extras.add(rs.getString("lane_change_assist"));
                extras.add(rs.getString("laser_headlights"));
                extras.add(rs.getString("leather_steering_wheel"));
                extras.add(rs.getString("led_headlights"));
                extras.add(rs.getString("lumbar_support"));
                extras.add(rs.getString("manual_climatisation"));
                extras.add(rs.getString("massage_seats"));
                extras.add(rs.getString("multifunction_steering_wheel"));
                extras.add(rs.getString("navigation_system"));
                extras.add(rs.getString("on_board_computer"));
                extras.add(rs.getString("paddle_shifters"));
                extras.add(rs.getString("panoramic_roof"));
                extras.add(rs.getString("parking_sensors"));
                extras.add(rs.getString("power_assisted_steering"));
                extras.add(rs.getString("rain_sensor"));
                extras.add(rs.getString("roof_rack"));
                extras.add(rs.getString("spare_tyre"));
                extras.add(rs.getString("sport_seats"));
                extras.add(rs.getString("start_stop_system"));
                extras.add(rs.getString("sunroof"));
                extras.add(rs.getString("traffic_sign_recognition"));
                extras.add(rs.getString("tyre_pressure_monitoring"));
                extras.add(rs.getString("usb_port"));
                extras.add(rs.getString("voice_control"));
            }
            return extras;
        }, extrasId);
    }
}