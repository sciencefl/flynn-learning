package com.sciencefl.flynn.filter;



public class JwtAuthFilter{
//        extends OncePerRequestFilter {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        if (request.getServletPath().equals("/api/v1/ssc/auth/token")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String token = resolveToken(request);
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            Claims claims = jwtTokenProvider.getClaimsFromToken(token);
//
//            // 关键步骤：转换 scopes 为 GrantedAuthority
//            Collection<? extends GrantedAuthority> authorities =
//                    mapScopesToAuthorities(claims.get("scopes", List.class));
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null,authorities);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private Collection<? extends GrantedAuthority> mapScopesToAuthorities(List<String> scopes) {
//        return scopes.stream()
//                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope)) // 添加前缀
//                .collect(Collectors.toList());
//    }
//
//    private String resolveToken(HttpServletRequest req) {
//        String bearerToken = req.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
}
