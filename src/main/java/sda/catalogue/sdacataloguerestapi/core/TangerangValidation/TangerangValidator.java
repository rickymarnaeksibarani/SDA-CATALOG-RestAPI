package sda.catalogue.sdacataloguerestapi.core.TangerangValidation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sda.catalogue.sdacataloguerestapi.core.TangerangValidation.TangerangAnnotation.*;

import java.lang.reflect.Field;
import java.util.*;

@Getter
@Setter
public class TangerangValidator extends RuntimeException {
    private static <T> Map<String, List<String>> TangerangValidation(T dto) {
        Map<String, List<String>> errors = new HashMap<>();
        Field[] fields = dto.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            //Validation for string or number not empty
            if (field.isAnnotationPresent(TGRNotEmpty.class)) {
                try {
                    Object value = field.get(dto);
                    if (value != null && value.toString().isEmpty()) {
                        String fieldName = field.getName();
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(field.getAnnotation(TGRNotEmpty.class).message());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }

            //Validation for minimum characters
            if (field.isAnnotationPresent(TGRMin.class)) {
                try {
                    Object value = field.get(dto);
                    if (value != null && value.toString().length() < field.getAnnotation(TGRMin.class).minValue()) {
                        String fieldName = field.getName();
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(field.getAnnotation(TGRMin.class).message());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }

            //Validation for maximum characters
            if (field.isAnnotationPresent(TGRMax.class)) {
                try {
                    Object value = field.get(dto);
                    if (value != null && value.toString().length() > field.getAnnotation(TGRMax.class).maxValue()) {
                        String fieldName = field.getName();
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(field.getAnnotation(TGRMax.class).message());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }

            //Validation for value type number
            if (field.isAnnotationPresent(TGRNumber.class)) {
                try {
                    Object value = field.get(dto);
                    if (value instanceof Number) {
                        String fieldName = field.getName();
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(field.getAnnotation(TGRNumber.class).message());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }

            //Validation for value type string
            if (field.isAnnotationPresent(TGRString.class)) {
                try {
                    Object value = field.get(dto);
                    if (!(value instanceof String)) {
                        String fieldName = field.getName();
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(field.getAnnotation(TGRString.class).message());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }

            //Validation for value type email with regex
            if (field.isAnnotationPresent(TGREmail.class)) {
                try {
                    Object value = field.get(dto);
                    if (value instanceof String && isValidEmail((String) Objects.requireNonNull(value))) {
                        String fieldName = field.getName();
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(field.getAnnotation(TGREmail.class).message());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }

            //Validation for value type string but number e.g "12345" with regex
            if (field.isAnnotationPresent(TGRStringNumber.class)) {
                try {
                    Object value = field.get(dto);
                    if (value instanceof String && !isNumeric((String) Objects.requireNonNull(value))) {
                        String fieldName = field.getName();
                        errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(field.getAnnotation(TGRStringNumber.class).message());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }
        }
        return errors;
    }

    public static <T> ResponseEntity<?> TangerangValidator(T dto) {
        Map<String, List<String>> errors = TangerangValidation(dto);
        if (!errors.isEmpty()) {
            TangerangError response = new TangerangError(HttpStatus.BAD_REQUEST, "Validation errors!", errors);
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.ok().build();
        }
    }


    // Email validation using regex
    private static boolean isValidEmail(String email) {
        // Define your email regex pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Numeric string check
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // Matches positive or negative integer or floating-point numbers
    }
}
