import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/service-option">
      <Translate contentKey="global.menu.entities.serviceOption" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/policy">
      <Translate contentKey="global.menu.entities.policy" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer-review">
      <Translate contentKey="global.menu.entities.customerReview" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/achievement">
      <Translate contentKey="global.menu.entities.achievement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-item">
      <Translate contentKey="global.menu.entities.serviceItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/blog">
      <Translate contentKey="global.menu.entities.blog" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/singleton-content">
      <Translate contentKey="global.menu.entities.singletonContent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/page">
      <Translate contentKey="global.menu.entities.page" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/person">
      <Translate contentKey="global.menu.entities.person" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/patient">
      <Translate contentKey="global.menu.entities.patient" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/doctor">
      <Translate contentKey="global.menu.entities.doctor" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/nurse">
      <Translate contentKey="global.menu.entities.nurse" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/receptionist">
      <Translate contentKey="global.menu.entities.receptionist" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/accountant">
      <Translate contentKey="global.menu.entities.accountant" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/material">
      <Translate contentKey="global.menu.entities.material" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/booking">
      <Translate contentKey="global.menu.entities.booking" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment">
      <Translate contentKey="global.menu.entities.payment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/medical-record">
      <Translate contentKey="global.menu.entities.medicalRecord" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/treatment">
      <Translate contentKey="global.menu.entities.treatment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/treatment-history">
      <Translate contentKey="global.menu.entities.treatmentHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/get-material">
      <Translate contentKey="global.menu.entities.getMaterial" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
