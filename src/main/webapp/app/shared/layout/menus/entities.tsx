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
    <MenuItem icon="asterisk" to="/customer">
      <Translate contentKey="global.menu.entities.customer" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee">
      <Translate contentKey="global.menu.entities.employee" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
