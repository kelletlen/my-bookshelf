package com.greenfoxacademy.mybookshelf.security;

import com.greenfoxacademy.mybookshelf.models.User;
import com.greenfoxacademy.mybookshelf.repositories.UserRepository;
import com.greenfoxacademy.mybookshelf.services.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  final JwtService jwtService;
  final UserRepository userRepository;

  public JwtTokenFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String header = "Authorization";
    getToken(request.getHeader(header)).flatMap(jwtService::extractUsernameFromToken).ifPresent(username -> {
      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        User user = userRepository.findByUsername(username);
        if (user.getUsername().equals(username)) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              user, null, user.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    });
    filterChain.doFilter(request, response);
  }

  public Optional<String> getToken(String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return Optional.of(authorizationHeader.substring(7));
    }
    return Optional.empty();
  }
}
