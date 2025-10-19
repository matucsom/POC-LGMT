package com.ensolvers.demo.AOP;


public class TracingUtils {

    public static String getCurrentUserId() {
        // Para el POC, devolvemos un valor fijo o pasado desde el contexto
        return "user-123"; // reemplazar por la lógica de tu POC
    }

    public static String getCurrentUserRoles() {
        // Para el POC, devolvemos un rol por defecto
        return "RH_EMPLOYEE";
    }
}





/*con Spring security*/

/*public class TracingUtils {

    public static String getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null) return "anonymous";

        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails user) {
            return user.getUsername();
        }
        return principal.toString();
    }

    public static String getCurrentUserRoles() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null) return "none";

        return context.getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}*/
