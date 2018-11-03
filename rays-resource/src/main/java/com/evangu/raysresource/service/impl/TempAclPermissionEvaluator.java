package com.evangu.raysresource.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

/**
 * @author: Gu danpeng
 * @data: 2018-11-4
 * @version：1.0
 */

public class TempAclPermissionEvaluator implements PermissionEvaluator {
        private final Log logger = LogFactory.getLog(this.getClass());
//        private final AclService aclService;
//        private PermissionFactory permissionFactory = new DefaultPermissionFactory();

        public TempAclPermissionEvaluator() {
        }

        public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
            if (domainObject == null) {
                return false;
            } else {
//                ObjectIdentity objectIdentity = this.objectIdentityRetrievalStrategy.getObjectIdentity(domainObject);
                Object objectIdentity = null;
                //this.checkPermission(authentication, objectIdentity, permission);
                return true;
            }
        }

        public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
//            ObjectIdentity objectIdentity = this.objectIdentityGenerator.createObjectIdentity(targetId, targetType);
//            return this.checkPermission(authentication, objectIdentity, permission);
            return true;
        }

//        private boolean checkPermission(Authentication authentication, ObjectIdentity oid, Object permission) {
//            List<Sid> sids = this.sidRetrievalStrategy.getSids(authentication);
//            List<Permission> requiredPermission = this.resolvePermission(permission);
//            boolean debug = this.logger.isDebugEnabled();
//            if (debug) {
//                this.logger.debug("Checking permission '" + permission + "' for object '" + oid + "'");
//            }
//
//            try {
//                Acl acl = this.aclService.readAclById(oid, sids);
//                if (acl.isGranted(requiredPermission, sids, false)) {
//                    if (debug) {
//                        this.logger.debug("Access is granted");
//                    }
//
//                    return true;
//                }
//
//                if (debug) {
//                    this.logger.debug("Returning false - ACLs returned, but insufficient permissions for this principal");
//                }
//            } catch (NotFoundException var8) {
//                if (debug) {
//                    this.logger.debug("Returning false - no ACLs apply for this principal");
//                }
//            }
//
//            return false;
//        }

//        List<Permission> resolvePermission(Object permission) {
//            if (permission instanceof Integer) {
//                return Arrays.asList(this.permissionFactory.buildFromMask((Integer)permission));
//            } else if (permission instanceof Permission) {
//                return Arrays.asList((Permission)permission);
//            } else if (permission instanceof Permission[]) {
//                return Arrays.asList((Permission[])((Permission[])permission));
//            } else {
//                if (permission instanceof String) {
//                    String permString = (String)permission;
//
//                    Permission p;
//                    try {
//                        p = this.permissionFactory.buildFromName(permString);
//                    } catch (IllegalArgumentException var5) {
//                        p = this.permissionFactory.buildFromName(permString.toUpperCase(Locale.ENGLISH));
//                    }
//
//                    if (p != null) {
//                        return Arrays.asList(p);
//                    }
//                }
//
//                throw new IllegalArgumentException("Unsupported permission: " + permission);
//            }
//        }
//
//        public void setObjectIdentityRetrievalStrategy(ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
//            this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
//        }
//
//        public void setObjectIdentityGenerator(ObjectIdentityGenerator objectIdentityGenerator) {
//            this.objectIdentityGenerator = objectIdentityGenerator;
//        }
//
//        public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy) {
//            this.sidRetrievalStrategy = sidRetrievalStrategy;
//        }
//
//        public void setPermissionFactory(PermissionFactory permissionFactory) {
//            this.permissionFactory = permissionFactory;
//        }
}
