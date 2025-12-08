# Deployment Fixes for BusMate Project

## Information Gathered
- CORS configuration had incorrect production URL (missing 's' in 'onlineticketingsystem')
- Frontend API base URL was set to localhost:8080, causing API calls to fail in production since frontend is embedded in backend
- OAuth2 redirect URL was hardcoded to localhost:3000, not using production URL
- Axios config used VITE_API_BASE_URL instead of VITE_API_URL as per SETUP.md

## Plan
1. Fix CORS allowed origins to match APP_BASE_URL
2. Set API_BASE_URL to empty string for embedded frontend
3. Update OAuth2 success handler to use APP_BASE_URL for redirects
4. Build and deploy the updated application to Render

## Dependent Files Edited
- backend/src/main/java/edu/cit/lgng/backend/config/CorsConfig.java
- web/src/api/axios.js
- backend/src/main/java/edu/cit/lgng/backend/config/OAuth2LoginSuccessHandler.java

## Followup Steps
1. Commit and push changes to GitHub
2. Trigger redeploy on Render
3. Test the deployed application for:
   - Frontend loading
   - API calls working
   - Authentication (login/signup/OAuth)
   - Protected routes
   - Admin functionality
