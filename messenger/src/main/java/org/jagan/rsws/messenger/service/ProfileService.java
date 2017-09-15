package org.jagan.rsws.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jagan.rsws.messenger.database.DatabaseClass;
import org.jagan.rsws.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("jagan", new Profile(1L, "jagan", "Jagan", "Baliah"));
	}
	
	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty()) return null;
		return profiles.put(profile.getProfileName(), profile);
	}
	
	public void removeProfile(String profileName) {
		profiles.remove(profileName);
	}
}
