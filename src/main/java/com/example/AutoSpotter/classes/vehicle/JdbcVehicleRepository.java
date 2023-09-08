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
        String sql = "INSERT INTO vehicle (manufacturer, model, body_type, color, registered, mileage, state, year, number_of_doors, number_of_wheels, " +
                    "maximum_allowable_weight, engine_type, motorcycle_engine_type, engine_displacement, engine_displacement_ccm3, engine_power, fuel_consumption, eco_category, transmission, drive_train, battery_capacity, " +
                    "charging_time, vehicle_range, city_id, vehicle_type_id, vehicle_safety_features_id, vehicle_extras_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                vehicle.getNumberOfDoors(),
                vehicle.getNumberOfWheels(),
                vehicle.getMaximumAllowableWeight(),
                vehicle.getEngineType(),
                vehicle.getMotorcycleEngineType(),
                vehicle.getEngineDisplacement(),
                vehicle.getEngineDisplacementCcm3(),
                vehicle.getEnginePower(),
                vehicle.getFuelConsumption(),
                vehicle.getEcoCategory(),
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
    public String getVehicleTypeNameById(int vehicleTypeId) {
        String sql = "SELECT name FROM vehicle_type WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, vehicleTypeId);
    }

    @Override
    public List<String> getAllVehicleTypes() {
        String sql = "SELECT name FROM vehicle_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public Vehicle getVehicleById(int id) {
        String sql = "SELECT id, manufacturer, model, body_type, color, registered, mileage, state, year, number_of_doors, number_of_wheels, maximum_allowable_weight, " +
                    "engine_type, motorcycle_engine_type, engine_displacement, engine_displacement_ccm3, engine_power, fuel_consumption, eco_category, transmission, drive_train, battery_capacity, charging_time, vehicle_range, " +
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
    public List<String> getAllMotorcycleEngineTypes() {
        String sql = "SELECT motorcycle_engine_type_name FROM motorcycle_engine_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllTransmissionTypes() {
        String sql = "SELECT transmission_name FROM transmission";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<String> getAllEcoCategories() {
        String sql = "SELECT eco_category_name FROM eco_category";
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
                    "air_suspension, alarm_system, aluminium_wheels, ambient_lighting, android_auto, " +
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
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] params = new Object[]{
            extras.contains("adaptive_cruise_control"),
            extras.contains("air_suspension"),
            extras.contains("alarm_system"),
            extras.contains("aluminium_wheels"),
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
    public List<SafetyFeature> getVehicleSafetyFeatures(int safetyFeaturesId) {
        String sql = "SELECT abs, esp, central_locking, traction_control, front_side_airbag, rear_side_airbag " +
                    "FROM vehicle_safety_features WHERE id = ?";
        return jdbcTemplate.query(sql, (ResultSetExtractor<List<SafetyFeature>>) rs -> {
            List<SafetyFeature> safetyFeatures = new ArrayList<>();
            if (rs.next()) {
                safetyFeatures.add(new SafetyFeature("Sustav protiv blokiranja kočnica (ABS)", rs.getBoolean("abs")));
                safetyFeatures.add(new SafetyFeature("Elektronički program stabilnosti (ESP)", rs.getBoolean("esp")));
                safetyFeatures.add(new SafetyFeature("Centralno zaključavanje", rs.getBoolean("central_locking")));
                safetyFeatures.add(new SafetyFeature("Sustav kontrole proklizavanja (Traction Control)", rs.getBoolean("traction_control")));
                safetyFeatures.add(new SafetyFeature("Prednji bočni zračni jastuci", rs.getBoolean("front_side_airbag")));
                safetyFeatures.add(new SafetyFeature("Stražnji bočni zračni jastuci", rs.getBoolean("rear_side_airbag")));
            }
            return safetyFeatures;
        }, safetyFeaturesId);
    }

    @Override
    public List<VehicleExtra> getVehicleExtras(int extrasId) {
        String sql = "SELECT adaptive_cruise_control, air_suspension, alarm_system, aluminium_wheels, ambient_lighting, android_auto, " +
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
        return jdbcTemplate.query(sql, (ResultSetExtractor<List<VehicleExtra>>) rs -> {
            List<VehicleExtra> extras = new ArrayList<>();
            if (rs.next()) {
                extras.add(new VehicleExtra("Prilagodljivi tempomat", rs.getBoolean("adaptive_cruise_control")));
                extras.add(new VehicleExtra("Zračni ovjes", rs.getBoolean("air_suspension")));
                extras.add(new VehicleExtra("Alarmni sustav", rs.getBoolean("alarm_system")));
                extras.add(new VehicleExtra("Aluminijske felge", rs.getBoolean("aluminium_wheels")));
                extras.add(new VehicleExtra("Ambijentalno osvjetljenje", rs.getBoolean("ambient_lighting")));
                extras.add(new VehicleExtra("Android Auto", rs.getBoolean("android_auto")));
                extras.add(new VehicleExtra("Apple CarPlay", rs.getBoolean("apple_carplay")));
                extras.add(new VehicleExtra("Oslonac za ruke", rs.getBoolean("arm_rest")));
                extras.add(new VehicleExtra("Automatska klima uređaj", rs.getBoolean("automatic_air_conditioning")));
                extras.add(new VehicleExtra("Automatska klima uređaj (2-zone)", rs.getBoolean("automatic_2_zone_climatisations")));
                extras.add(new VehicleExtra("Automatska klima uređaj (3-zone)", rs.getBoolean("automatic_3_zones_climatisation")));
                extras.add(new VehicleExtra("Automatska klima uređaj (4-zone)", rs.getBoolean("automatic_4_zones_climatisation")));
                extras.add(new VehicleExtra("Bi-ksenonska svjetla", rs.getBoolean("bi_xenon_headlights")));
                extras.add(new VehicleExtra("Bluetooth", rs.getBoolean("bluetooth")));
                extras.add(new VehicleExtra("Pomoć pri mrtvom kutu", rs.getBoolean("blind_spot_assist")));
                extras.add(new VehicleExtra("CD player", rs.getBoolean("cd_player")));
                extras.add(new VehicleExtra("Tempomat", rs.getBoolean("cruise_control")));
                extras.add(new VehicleExtra("DAB radio", rs.getBoolean("dab_radio")));
                extras.add(new VehicleExtra("Sustav upozorenja na udaljenost", rs.getBoolean("distance_warning_system")));
                extras.add(new VehicleExtra("Podešavanje električnih sjedala", rs.getBoolean("electric_seat_adjustment")));
                extras.add(new VehicleExtra("Električno podesivi bočni retrovizori", rs.getBoolean("electric_side_mirror")));
                extras.add(new VehicleExtra("Električni prozori", rs.getBoolean("electric_windows")));
                extras.add(new VehicleExtra("Sustav za hitne pozive", rs.getBoolean("emergency_call_system")));
                extras.add(new VehicleExtra("Sustav za popravak guma u nuždi", rs.getBoolean("emergency_tyre_repair_kit")));
                extras.add(new VehicleExtra("Maglenke", rs.getBoolean("fog_lamp")));
                extras.add(new VehicleExtra("Hands-free uređaj", rs.getBoolean("hands_free_kit")));
                extras.add(new VehicleExtra("Head-Up zaslon", rs.getBoolean("head_up_display")));
                extras.add(new VehicleExtra("Sustav za pranje prednjih svjetala", rs.getBoolean("headlight_washer_system")));
                extras.add(new VehicleExtra("Grijanje stražnjih sjedala", rs.getBoolean("heated_rear_seats")));
                extras.add(new VehicleExtra("Grijanje sjedala", rs.getBoolean("heated_seats")));
                extras.add(new VehicleExtra("Grijanje upravljačkog kotača", rs.getBoolean("heated_steering_wheel")));
                extras.add(new VehicleExtra("Sustav za pomoć pri kretanju na uzbrdici", rs.getBoolean("hill_start_assist")));
                extras.add(new VehicleExtra("Indukcijsko punjenje pametnih telefona", rs.getBoolean("induction_charging_for_smartphones")));
                extras.add(new VehicleExtra("ISOFIX sustav", rs.getBoolean("isofix")));
                extras.add(new VehicleExtra("Centralno zaključavanje bez ključa", rs.getBoolean("keyless_central_locking")));
                extras.add(new VehicleExtra("Sustav za asistenciju kod mijenjanja trake", rs.getBoolean("lane_change_assist")));
                extras.add(new VehicleExtra("Laser svjetla", rs.getBoolean("laser_headlights")));
                extras.add(new VehicleExtra("Kožni volan", rs.getBoolean("leather_steering_wheel")));
                extras.add(new VehicleExtra("LED svjetla", rs.getBoolean("led_headlights")));
                extras.add(new VehicleExtra("Potpora za leđa", rs.getBoolean("lumbar_support")));
                extras.add(new VehicleExtra("Ručno podešavanje klime", rs.getBoolean("manual_climatisation")));
                extras.add(new VehicleExtra("Masažna sjedala", rs.getBoolean("massage_seats")));
                extras.add(new VehicleExtra("Višenamjenski volan", rs.getBoolean("multifunction_steering_wheel")));
                extras.add(new VehicleExtra("Navigacijski sustav", rs.getBoolean("navigation_system")));
                extras.add(new VehicleExtra("On-Board računalo", rs.getBoolean("on_board_computer")));
                extras.add(new VehicleExtra("Mjenjačke lopatice iza volana", rs.getBoolean("paddle_shifters")));
                extras.add(new VehicleExtra("Panoramski krov", rs.getBoolean("panoramic_roof")));
                extras.add(new VehicleExtra("Senzori za parkiranje", rs.getBoolean("parking_sensors")));
                extras.add(new VehicleExtra("Servo upravljanje", rs.getBoolean("power_assisted_steering")));
                extras.add(new VehicleExtra("Senzor za kišu", rs.getBoolean("rain_sensor")));
                extras.add(new VehicleExtra("Krovni nosači", rs.getBoolean("roof_rack")));
                extras.add(new VehicleExtra("Rezervni kotač", rs.getBoolean("spare_tyre")));
                extras.add(new VehicleExtra("Sportska sjedala", rs.getBoolean("sport_seats")));
                extras.add(new VehicleExtra("Sustav za automatsko zaustavljanje motora", rs.getBoolean("start_stop_system")));
                extras.add(new VehicleExtra("Sunroof", rs.getBoolean("sunroof")));
                extras.add(new VehicleExtra("Prepoznavanje prometnih znakova", rs.getBoolean("traffic_sign_recognition")));
                extras.add(new VehicleExtra("Sustav za praćenje tlaka u gumama", rs.getBoolean("tyre_pressure_monitoring")));
                extras.add(new VehicleExtra("USB priključak", rs.getBoolean("usb_port")));
                extras.add(new VehicleExtra("Glasovno upravljanje", rs.getBoolean("voice_control")));
            }
            return extras;
        }, extrasId);
    }
}