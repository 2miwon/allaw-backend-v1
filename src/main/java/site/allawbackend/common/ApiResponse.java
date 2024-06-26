package site.allawbackend.common;

public record ApiResponse<T>(
        T data,
        String message
) {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message);
    }


}
